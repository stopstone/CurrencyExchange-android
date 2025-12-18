package com.codingtestmobile.currencyexchange.data.remote.response

import com.google.gson.annotations.SerializedName

data class ExchangeRateResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("timestamp") val timestamp: Long,
    @SerializedName("source") val source: String,
    @SerializedName("quotes") val quotes: Map<String, Double>,
)
