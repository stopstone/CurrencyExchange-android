package com.codingtestmobile.currencyexchange.domain.usecase

import com.codingtestmobile.currencyexchange.domain.model.ExchangeRate
import com.codingtestmobile.currencyexchange.domain.repository.ExchangeRateRepository
import javax.inject.Inject

class GetExchangeRateUseCase
    @Inject
    constructor(
        private val repository: ExchangeRateRepository,
    ) {
        suspend operator fun invoke(): ExchangeRate = repository.getExchangeRates()
    }
