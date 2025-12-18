package com.codingtestmobile.currencyexchange.util

import org.junit.Assert.assertEquals
import org.junit.Test

class CurrencyFormatterTest {
    @Test
    fun `소수점 2자리 포맷`() {
        // Given
        val value = 1234.0

        // When
        val result = value.toCurrencyFormat()

        // Then
        assertEquals("1,234.00", result)
    }

    @Test
    fun `3자리 콤마 포맷`() {
        // Given
        val value = 1234567.89

        // When
        val result = value.toCurrencyFormat()

        // Then
        assertEquals("1,234,567.89", result)
    }

    @Test
    fun `소수점 반올림`() {
        // Given
        val value = 1234.567

        // When
        val result = value.toCurrencyFormat()

        // Then
        assertEquals("1,234.57", result)
    }

    @Test
    fun `0 포맷`() {
        // Given
        val value = 0.0

        // When
        val result = value.toCurrencyFormat()

        // Then
        assertEquals("0.00", result)
    }

    @Test
    fun `큰 숫자 포맷`() {
        // Given
        val value = 13000000.0

        // When
        val result = value.toCurrencyFormat()

        // Then
        assertEquals("13,000,000.00", result)
    }
}
