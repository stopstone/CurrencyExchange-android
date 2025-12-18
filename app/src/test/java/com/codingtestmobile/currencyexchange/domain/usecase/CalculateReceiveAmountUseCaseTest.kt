package com.codingtestmobile.currencyexchange.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

class CalculateReceiveAmountUseCaseTest {

    private lateinit var useCase: CalculateReceiveAmountUseCase

    @Before
    fun setup() {
        useCase = CalculateReceiveAmountUseCase()
    }

    @Test
    fun `정상 금액 계산 성공`() {
        // Given
        val amount = 100.0
        val rate = 1300.0

        // When
        val result = useCase(amount, rate)

        // Then
        assertEquals(130000.0, result, 0.01)
    }

    @Test
    fun `최소 금액 경계값 테스트 - 0 초과`() {
        // Given
        val amount = 0.01
        val rate = 1300.0

        // When
        val result = useCase(amount, rate)

        // Then
        assertEquals(13.0, result, 0.01)
    }

    @Test
    fun `최대 금액 경계값 테스트 - 10000 이하`() {
        // Given
        val amount = 10000.0
        val rate = 1300.0

        // When
        val result = useCase(amount, rate)

        // Then
        assertEquals(13000000.0, result, 0.01)
    }

    @Test
    fun `0 이하 금액은 에러 발생`() {
        // Given
        val amount = 0.0
        val rate = 1300.0

        // When & Then
        assertThrows(IllegalArgumentException::class.java) {
            useCase(amount, rate)
        }
    }

    @Test
    fun `음수 금액은 에러 발생`() {
        // Given
        val amount = -100.0
        val rate = 1300.0

        // When & Then
        assertThrows(IllegalArgumentException::class.java) {
            useCase(amount, rate)
        }
    }

    @Test
    fun `10000 초과 금액은 에러 발생`() {
        // Given
        val amount = 10001.0
        val rate = 1300.0

        // When & Then
        assertThrows(IllegalArgumentException::class.java) {
            useCase(amount, rate)
        }
    }

    @Test
    fun `에러 메시지 확인`() {
        // Given
        val amount = 0.0
        val rate = 1300.0

        // When & Then
        val exception = assertThrows(IllegalArgumentException::class.java) {
            useCase(amount, rate)
        }
        assertEquals("송금액이 바르지 않습니다", exception.message)
    }
}

