package com.codingtestmobile.currencyexchange.feature.currency_exchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingtestmobile.currencyexchange.domain.model.Country
import com.codingtestmobile.currencyexchange.domain.model.ExchangeRate
import com.codingtestmobile.currencyexchange.domain.usecase.CalculateReceiveAmountUseCase
import com.codingtestmobile.currencyexchange.domain.usecase.GetExchangeRateUseCase
import com.codingtestmobile.currencyexchange.feature.currency_exchange.state.ExchangeRateState
import com.codingtestmobile.currencyexchange.feature.currency_exchange.state.ExchangeUiEvent
import com.codingtestmobile.currencyexchange.feature.currency_exchange.state.ExchangeUiState
import com.codingtestmobile.currencyexchange.util.InputValidator
import com.codingtestmobile.currencyexchange.util.toCurrencyFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel
    @Inject
    constructor(
        private val getExchangeRateUseCase: GetExchangeRateUseCase,
        private val calculateReceiveAmountUseCase: CalculateReceiveAmountUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(ExchangeUiState())
        val uiState: StateFlow<ExchangeUiState> = _uiState.asStateFlow()

        init {
            loadExchangeRate()
        }

        fun handleEvent(event: ExchangeUiEvent) {
            when (event) {
                is ExchangeUiEvent.OnCountrySelected -> onCountrySelected(event.country)
                is ExchangeUiEvent.OnAmountChanged -> onAmountChanged(event.amount)
                is ExchangeUiEvent.OnLoadExchangeRate -> loadExchangeRate()
            }
        }

        private fun onCountrySelected(country: Country) {
            _uiState.update { it.copy(selectedCountry = country) }
            calculateReceiveAmount()
        }

        private fun onAmountChanged(amount: String) {
            _uiState.update { it.copy(sendAmount = amount, errorMessage = null) }
            calculateReceiveAmount()
        }

        private fun calculateReceiveAmount() {
            val currentState = _uiState.value
            val exchangeRateState = currentState.exchangeRate

            // 환율 정보가 없으면 계산 불가
            if (exchangeRateState !is ExchangeRateState.Success) {
                _uiState.update { it.copy(receiveAmount = "") }
                return
            }

            val sendAmount = InputValidator.toDoubleOrNull(currentState.sendAmount)
            if (sendAmount == null || sendAmount <= 0) {
                _uiState.update { it.copy(receiveAmount = "", errorMessage = null) }
                return
            }

            val exchangeRate =
                ExchangeRate(
                    krw = exchangeRateState.krw,
                    jpy = exchangeRateState.jpy,
                    php = exchangeRateState.php,
                    timestamp = exchangeRateState.timestamp,
                )

            val rate = exchangeRate.getRate(currentState.selectedCountry)

            try {
                val receiveAmount = calculateReceiveAmountUseCase(sendAmount, rate)
                _uiState.update {
                    it.copy(
                        receiveAmount = receiveAmount.toCurrencyFormat(),
                        errorMessage = null,
                    )
                }
            } catch (e: IllegalArgumentException) {
                _uiState.update {
                    it.copy(
                        receiveAmount = "",
                        errorMessage = e.message ?: "송금액이 바르지 않습니다",
                    )
                }
            }
        }

        private fun loadExchangeRate() {
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true, exchangeRate = ExchangeRateState.Loading) }

                try {
                    val exchangeRate = getExchangeRateUseCase()
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            exchangeRate =
                                ExchangeRateState.Success(
                                    krw = exchangeRate.krw,
                                    jpy = exchangeRate.jpy,
                                    php = exchangeRate.php,
                                    timestamp = exchangeRate.timestamp,
                                ),
                        )
                    }
                    // 환율 로드 후 수취금액 재계산
                    calculateReceiveAmount()
                } catch (e: Exception) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            exchangeRate =
                                ExchangeRateState.Error(
                                    message = e.message ?: "환율 정보를 불러올 수 없습니다",
                                ),
                        )
                    }
                }
            }
        }
    }
