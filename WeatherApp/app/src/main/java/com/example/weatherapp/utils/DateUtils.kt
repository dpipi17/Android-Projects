package com.example.weatherapp.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {

    companion object {

        fun isDay(date: Date) : Boolean {
            val formatter = SimpleDateFormat("HH", Locale.ENGLISH)
            formatter.timeZone = TimeZone.getTimeZone("GMT");
            var hour = formatter.format(date).toInt()
            return hour in 6..17
        }

        fun getDateText(date: Date, pattern: String) : String {
            val formatter = SimpleDateFormat(pattern, Locale.ENGLISH)
            formatter.timeZone = TimeZone.getTimeZone("GMT");
            return formatter.format(date)
        }

        fun getDateText(seconds: Long, pattern: String) : String {
            return getDateText(Date(seconds * 1000), pattern)
        }

    }
}