package com.codingtestmobile.currencyexchange.data.remote.datasource

import com.codingtestmobile.currencyexchange.BuildConfig
import com.codingtestmobile.currencyexchange.data.remote.api.ExchangeRateApi
import com.codingtestmobile.currencyexchange.data.remote.response.ExchangeRateResponse
import javax.inject.Inject

class ExchangeRateRemoteDataSource
    @Inject
    constructor(
        private val api: ExchangeRateApi,
    ) {
        suspend fun getExchangeRates(): ExchangeRateResponse = api.getExchangeRates(BuildConfig.API_KEY)
    }
