package com.codingtestmobile.currencyexchange.domain.model

// 수취 국가 enum
enum class Country(
    val currencyCode: String,
    val displayName: String,
) {
    KOREA("KRW", "한국(KRW)"),
    JAPAN("JPY", "일본(JPY)"),
    PHILIPPINES("PHP", "필리핀(PHP)"),
}
