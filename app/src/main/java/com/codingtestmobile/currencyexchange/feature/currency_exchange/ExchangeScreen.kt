package com.codingtestmobile.currencyexchange.feature.currency_exchange

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codingtestmobile.currencyexchange.domain.model.Country
import com.codingtestmobile.currencyexchange.feature.currency_exchange.components.CountryPicker
import com.codingtestmobile.currencyexchange.feature.currency_exchange.components.ExchangeInfoRow
import com.codingtestmobile.currencyexchange.feature.currency_exchange.state.ExchangeRateState
import com.codingtestmobile.currencyexchange.feature.currency_exchange.state.ExchangeUiEvent
import com.codingtestmobile.currencyexchange.feature.currency_exchange.state.ExchangeUiState
import com.codingtestmobile.currencyexchange.ui.theme.CurrencyExchangeTheme
import com.codingtestmobile.currencyexchange.util.toCurrencyFormat
import com.codingtestmobile.currencyexchange.util.toDateTimeFormat

@Composable
fun ExchangeScreen(
    viewModel: ExchangeViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()

    ExchangeContent(
        uiState = uiState,
        onCountrySelected = { country ->
            viewModel.handleEvent(ExchangeUiEvent.OnCountrySelected(country))
        },
        onAmountChanged = { amount ->
            viewModel.handleEvent(ExchangeUiEvent.OnAmountChanged(amount))
        },
        modifier = modifier,
    )
}

@Composable
fun ExchangeContent(
    uiState: ExchangeUiState,
    onCountrySelected: (Country) -> Unit,
    onAmountChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val exchangeRateText =
        when (val rateState = uiState.exchangeRate) {
            is ExchangeRateState.Success -> {
                val rate =
                    when (uiState.selectedCountry) {
                        Country.KOREA -> rateState.krw
                        Country.JAPAN -> rateState.jpy
                        Country.PHILIPPINES -> rateState.php
                    }
                rate.toCurrencyFormat()
            }

            is ExchangeRateState.Error -> {
                rateState.message
            }

            is ExchangeRateState.Loading -> {
                "로딩 중..."
            }
        }
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.padding(vertical = 32.dp),
            text = "환율 계산",
            style = MaterialTheme.typography.displayMedium,
        )

        // 송금국가
        ExchangeInfoRow(
            label = "송금국가 : ",
            value = "미국(USD)",
        )

        // 수취국가 (선택된 국가 표시)
        ExchangeInfoRow(
            label = "수취국가 : ",
            value = uiState.selectedCountry.displayName,
        )

        // 환율 표시
        ExchangeInfoRow(
            label = "환율 : ",
            value = "$exchangeRateText ${uiState.selectedCountry.currencyCode}/USD",
        )

        // 조회시간 표시
        if (uiState.exchangeRate is ExchangeRateState.Success) {
            ExchangeInfoRow(
                label = "조회시간 : ",
                value = uiState.exchangeRate.timestamp.toDateTimeFormat(),
            )
        }

        // 송금액 입력
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "송금액 : ",
                modifier = Modifier.width(80.dp),
                textAlign = TextAlign.End,
            )
            OutlinedTextField(
                value = uiState.sendAmount,
                onValueChange = onAmountChanged,
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp),
                label = { Text("USD") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
            )
            Text(
                modifier = Modifier.padding(horizontal = 4.dp),
                text = "USD",
            )
        }

        Spacer(modifier = Modifier.height(64.dp))

        // 수취금액 표시
        if (uiState.errorMessage != null) {
            Text(
                text = uiState.errorMessage,
                fontSize = 18.sp,
                color = Color.Red,
            )
        } else if (uiState.receiveAmount.isNotEmpty()) {
            Text(
                text = "수취금액은 ${uiState.receiveAmount} ${uiState.selectedCountry.currencyCode} 입니다",
                fontSize = 18.sp,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // 수취국가 선택 휠피커
        CountryPicker(
            selectedCountry = uiState.selectedCountry,
            onCountrySelected = onCountrySelected,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ExchangeContentPreview() {
    CurrencyExchangeTheme {
        ExchangeContent(
            uiState =
                ExchangeUiState(
                    selectedCountry = Country.KOREA,
                    exchangeRate =
                        ExchangeRateState.Success(
                            krw = 1300.0,
                            jpy = 157.5,
                            php = 56.0,
                            timestamp = 1553070000L, // 2019-03-20 16:20
                        ),
                    sendAmount = "100",
                    receiveAmount = "130,000.00",
                    errorMessage = null,
                ),
            onCountrySelected = {},
            onAmountChanged = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ExchangeContentErrorPreview() {
    CurrencyExchangeTheme {
        ExchangeContent(
            uiState =
                ExchangeUiState(
                    selectedCountry = Country.JAPAN,
                    exchangeRate =
                        ExchangeRateState.Success(
                            krw = 1300.0,
                            jpy = 157.5,
                            php = 56.0,
                            timestamp = 1553070000L, // 2019-03-20 16:20
                        ),
                    sendAmount = "10001",
                    receiveAmount = "",
                    errorMessage = "송금액이 바르지 않습니다",
                ),
            onCountrySelected = {},
            onAmountChanged = {},
        )
    }
}
