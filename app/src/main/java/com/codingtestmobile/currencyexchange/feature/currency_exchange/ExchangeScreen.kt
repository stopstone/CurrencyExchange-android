package com.codingtestmobile.currencyexchange.feature.currency_exchange

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codingtestmobile.currencyexchange.domain.model.Country
import com.codingtestmobile.currencyexchange.feature.components.CountryPicker
import com.codingtestmobile.currencyexchange.feature.components.ExchangeInfoRow
import com.codingtestmobile.currencyexchange.ui.theme.CurrencyExchangeTheme

@Composable
fun ExchangeScreen(modifier: Modifier = Modifier) {
    // TODO: ViewModel 연결
    ExchangeContent(
        selectedCountry = Country.KOREA,
        exchangeRate = "1,300.00",
        sendAmount = "",
        receiveAmount = "130,000.00",
        errorMessage = null,
        onCountrySelected = { /* TODO */ },
        onAmountChanged = { /* TODO */ },
        modifier = modifier,
    )
}

@Composable
fun ExchangeContent(
    selectedCountry: Country,
    exchangeRate: String,
    sendAmount: String,
    receiveAmount: String,
    errorMessage: String?,
    onAmountChanged: (String) -> Unit,
    onCountrySelected: () -> Unit,
    modifier: Modifier = Modifier,
) {
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
            label = "송금국가:",
            value = "미국(USD)",
        )

        // 수취국가 (선택된 국가 표시)
        ExchangeInfoRow(
            label = "수취국가:",
            value = selectedCountry.displayName,
        )

        // 환율 표시
        ExchangeInfoRow(
            label = "환율:",
            value = "$exchangeRate ${selectedCountry.currencyCode}/USD",
        )

        // 송금액 입력
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "송금액:")
            OutlinedTextField(
                value = sendAmount,
                onValueChange = onAmountChanged,
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                label = { Text("USD") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
            )
            Text(text = "USD")
        }

        Spacer(modifier = Modifier.height(64.dp))

        // 수취금액 표시
        if (errorMessage != null) {
            Text(
                text = errorMessage,
                fontSize = 18.sp,
                color = Color.Red,
            )
        } else if (receiveAmount.isNotEmpty()) {
            Text(
                text = "수취금액은 $receiveAmount ${selectedCountry.currencyCode} 입니다",
                fontSize = 18.sp,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // 수취국가 선택 휠피커
        CountryPicker(
            selectedCountry = selectedCountry,
        ) {
            onCountrySelected()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExchangeContentPreview() {
    CurrencyExchangeTheme {
        ExchangeContent(
            selectedCountry = Country.KOREA,
            exchangeRate = "1,300.00",
            sendAmount = "100",
            receiveAmount = "130,000.00",
            errorMessage = null,
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
            selectedCountry = Country.JAPAN,
            exchangeRate = "157.50",
            sendAmount = "10001",
            receiveAmount = "",
            errorMessage = "송금액이 바르지 않습니다",
            onCountrySelected = {},
            onAmountChanged = {},
        )
    }
}
