package com.codingtestmobile.currencyexchange.di

import com.codingtestmobile.currencyexchange.data.repository.ExchangeRateRepositoryImpl
import com.codingtestmobile.currencyexchange.domain.repository.ExchangeRateRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindExchangeRateRepository(
        impl: ExchangeRateRepositoryImpl,
    ): ExchangeRateRepository
}

