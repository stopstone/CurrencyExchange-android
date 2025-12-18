package com.codingtestmobile.currencyexchange.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * timestamp를 "yyyy-MM-dd HH:mm" 형식으로 변환
 */
fun Long.toDateTimeFormat(): String {
    val date = Date(this * 1000) // timestamp는 초 단위이므로 밀리초로 변환
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    return format.format(date)
}
