package com.codingtestmobile.currencyexchange.data.mapper

import com.codingtestmobile.currencyexchange.data.remote.response.ExchangeRateResponse
import com.codingtestmobile.currencyexchange.domain.model.ExchangeRate

object ExchangeRateMapper {
    private const val KEY_USD_KRW = "USDKRW"
    private const val KEY_USD_JPY = "USDJPY"
    private const val KEY_USD_PHP = "USDPHP"
    private const val DEFAULT_RATE = 0.0

    fun toDomain(response: ExchangeRateResponse): ExchangeRate =
        ExchangeRate(
            krw = response.quotes[KEY_USD_KRW] ?: DEFAULT_RATE,
            jpy = response.quotes[KEY_USD_JPY] ?: DEFAULT_RATE,
            php = response.quotes[KEY_USD_PHP] ?: DEFAULT_RATE,
        )
}
