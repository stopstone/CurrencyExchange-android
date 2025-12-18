package com.codingtestmobile.currencyexchange.feature.currency_exchange.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.codingtestmobile.currencyexchange.domain.model.Country
import com.codingtestmobile.currencyexchange.ui.components.TextWheelPicker

@Composable
fun CountryPicker(
    selectedCountry: Country,
    onCountrySelected: (Country) -> Unit,
) {
    TextWheelPicker(
        items = Country.entries,
        initialIndex = Country.entries.indexOf(selectedCountry),
        onItemSelected = { _, country -> onCountrySelected(country) },
        modifier =
            Modifier
                .fillMaxWidth(),
        visibleItemCount = Country.entries.size,
        itemToString = { it.displayName },
    )
}
