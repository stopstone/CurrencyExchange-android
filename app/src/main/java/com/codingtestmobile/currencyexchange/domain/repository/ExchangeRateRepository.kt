package com.codingtestmobile.currencyexchange.domain.repository

import com.codingtestmobile.currencyexchange.domain.model.ExchangeRate

// 환율 Repository 인터페이스
interface ExchangeRateRepository {
    suspend fun getExchangeRates(): ExchangeRate
}
