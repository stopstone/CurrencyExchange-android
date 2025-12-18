package com.codingtestmobile.currencyexchange.feature.currency_exchange.state

import com.codingtestmobile.currencyexchange.domain.model.Country

/**
 * 환율 계산 화면 UI 상태
 */
data class ExchangeUiState(
    val selectedCountry: Country = Country.KOREA,
    val exchangeRate: ExchangeRateState = ExchangeRateState.Loading,
    val sendAmount: String = "",
    val receiveAmount: String = "",
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
)

/**
 * 환율 정보 상태
 */
sealed interface ExchangeRateState {
    data object Loading : ExchangeRateState

    data class Success(
        val krw: Double,
        val jpy: Double,
        val php: Double,
        val timestamp: Long,
    ) : ExchangeRateState

    data class Error(
        val message: String,
    ) : ExchangeRateState
}
