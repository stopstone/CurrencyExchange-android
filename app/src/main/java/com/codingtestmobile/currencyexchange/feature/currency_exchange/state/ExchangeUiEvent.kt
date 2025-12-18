package com.codingtestmobile.currencyexchange.feature.currency_exchange.state

import com.codingtestmobile.currencyexchange.domain.model.Country

sealed interface ExchangeUiEvent {
    data class OnCountrySelected(
        val country: Country,
    ) : ExchangeUiEvent

    data class OnAmountChanged(
        val amount: String,
    ) : ExchangeUiEvent

    data object OnLoadExchangeRate : ExchangeUiEvent
}
