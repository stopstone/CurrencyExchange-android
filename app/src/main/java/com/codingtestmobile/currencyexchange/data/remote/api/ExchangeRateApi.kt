package com.codingtestmobile.currencyexchange.data.remote.api

import com.codingtestmobile.currencyexchange.data.remote.response.ExchangeRateResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRateApi {
    @GET("live")
    suspend fun getExchangeRates(
        @Query("access_key") accessKey: String,
    ): ExchangeRateResponse
}
