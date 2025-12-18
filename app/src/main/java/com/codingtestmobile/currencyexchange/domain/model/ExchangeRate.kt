package com.codingtestmobile.currencyexchange.domain.model

/**
 * 환율 도메인 모델
 *
 * USD 기준 각 통화의 환율을 담고 있음
 * 예: krw = 1300.0 → 1 USD = 1300 KRW
 */
data class ExchangeRate(
    val krw: Double,
    val jpy: Double,
    val php: Double,
) {
    // 선택된 국가의 환율 반환
    fun getRate(country: Country): Double =
        when (country) {
            Country.KOREA -> krw
            Country.JAPAN -> jpy
            Country.PHILIPPINES -> php
        }
}
