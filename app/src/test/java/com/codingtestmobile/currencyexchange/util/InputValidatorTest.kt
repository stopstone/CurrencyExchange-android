package com.codingtestmobile.currencyexchange.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class InputValidatorTest {

    @Test
    fun `유효한 금액 - 정상 범위`() {
        assertTrue(InputValidator.isValidAmount("100"))
        assertTrue(InputValidator.isValidAmount("1"))
        assertTrue(InputValidator.isValidAmount("10000"))
        assertTrue(InputValidator.isValidAmount("0.01"))
    }

    @Test
    fun `유효하지 않은 금액 - 0 이하`() {
        assertFalse(InputValidator.isValidAmount("0"))
        assertFalse(InputValidator.isValidAmount("-1"))
        assertFalse(InputValidator.isValidAmount("-100"))
    }

    @Test
    fun `유효하지 않은 금액 - 10000 초과`() {
        assertFalse(InputValidator.isValidAmount("10001"))
        assertFalse(InputValidator.isValidAmount("99999"))
    }

    @Test
    fun `유효하지 않은 금액 - 숫자가 아닌 입력`() {
        assertFalse(InputValidator.isValidAmount("abc"))
        assertFalse(InputValidator.isValidAmount(""))
        assertFalse(InputValidator.isValidAmount("12a34"))
    }

    @Test
    fun `toDoubleOrNull - 정상 변환`() {
        assertEquals(100.0, InputValidator.toDoubleOrNull("100"))
        assertEquals(0.5, InputValidator.toDoubleOrNull("0.5"))
    }

    @Test
    fun `toDoubleOrNull - 변환 실패시 null`() {
        assertNull(InputValidator.toDoubleOrNull("abc"))
        assertNull(InputValidator.toDoubleOrNull(""))
    }
}

