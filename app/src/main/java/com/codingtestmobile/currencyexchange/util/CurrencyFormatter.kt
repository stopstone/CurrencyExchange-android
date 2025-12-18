package com.codingtestmobile.currencyexchange.util

fun Double.toCurrencyFormat(): String = String.format("%,.2f", this)
