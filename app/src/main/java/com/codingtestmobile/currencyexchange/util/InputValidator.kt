package com.codingtestmobile.currencyexchange.util

object InputValidator {

    private const val MIN_AMOUNT = 0.0
    private const val MAX_AMOUNT = 10_000.0

    fun isValidAmount(input: String): Boolean {
        val amount = input.toDoubleOrNull() ?: return false
        return amount > MIN_AMOUNT && amount <= MAX_AMOUNT
    }

    fun toDoubleOrNull(input: String): Double? = input.toDoubleOrNull()
}

