package com.codingtestmobile.currencyexchange.data.repository

import com.codingtestmobile.currencyexchange.data.mapper.ExchangeRateMapper
import com.codingtestmobile.currencyexchange.data.remote.datasource.ExchangeRateRemoteDataSource
import com.codingtestmobile.currencyexchange.domain.model.ExchangeRate
import com.codingtestmobile.currencyexchange.domain.repository.ExchangeRateRepository
import java.io.IOException
import javax.inject.Inject

class ExchangeRateRepositoryImpl
    @Inject
    constructor(
        private val remoteDataSource: ExchangeRateRemoteDataSource,
    ) : ExchangeRateRepository {
        override suspend fun getExchangeRates(): ExchangeRate =
            runCatching { remoteDataSource.getExchangeRates() }
                .map { response ->
                    require(response.success) { "환율 정보를 불러올 수 없습니다" }
                    ExchangeRateMapper.toDomain(response)
                }.getOrElse { throw IOException("네트워크 연결을 확인해주세요") }
    }
