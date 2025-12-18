package com.codingtestmobile.currencyexchange.data.mapper

import com.codingtestmobile.currencyexchange.data.remote.response.ExchangeRateResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class ExchangeRateMapperTest {

    @Test
    fun `정상적인 응답을 도메인 모델로 변환`() {
        // Given
        val response = ExchangeRateResponse(
            success = true,
            timestamp = 1234567890L,
            source = "USD",
            quotes = mapOf(
                "USDKRW" to 1300.0,
                "USDJPY" to 150.0,
                "USDPHP" to 55.0,
            ),
        )

        // When
        val result = ExchangeRateMapper.toDomain(response)

        // Then
        assertEquals(1300.0, result.krw, 0.01)
        assertEquals(150.0, result.jpy, 0.01)
        assertEquals(55.0, result.php, 0.01)
    }

    @Test
    fun `quotes에 KRW가 없으면 기본값 0 반환`() {
        // Given
        val response = ExchangeRateResponse(
            success = true,
            timestamp = 1234567890L,
            source = "USD",
            quotes = mapOf(
                "USDJPY" to 150.0,
                "USDPHP" to 55.0,
            ),
        )

        // When
        val result = ExchangeRateMapper.toDomain(response)

        // Then
        assertEquals(0.0, result.krw, 0.01)
    }

    @Test
    fun `빈 quotes는 모든 값이 기본값 0`() {
        // Given
        val response = ExchangeRateResponse(
            success = true,
            timestamp = 1234567890L,
            source = "USD",
            quotes = emptyMap(),
        )

        // When
        val result = ExchangeRateMapper.toDomain(response)

        // Then
        assertEquals(0.0, result.krw, 0.01)
        assertEquals(0.0, result.jpy, 0.01)
        assertEquals(0.0, result.php, 0.01)
    }
}

