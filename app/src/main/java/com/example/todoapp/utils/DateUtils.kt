package com.example.todoapp.utils

import java.text.DateFormat
import java.util.*

class DateUtils {
    private val dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())

    fun formatDateToString(date: Long): String = dateFormat.format(Date(date))

    fun formatDateToLong(date: String): Long = dateFormat.parse(date)?.time ?: 0

    fun formatDatePickerDateToString(year: Int, month: Int, day: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        return formatDateToString(calendar.timeInMillis)
    }

    fun isDateInPast(date: Long): Boolean {
        val currentTime = System.currentTimeMillis()
        return date < currentTime
    }
}