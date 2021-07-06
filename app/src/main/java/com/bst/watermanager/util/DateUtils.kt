package com.bst.watermanager.util

import java.util.Date
import java.util.Calendar
import java.util.Locale

object DateUtils {
    fun getCurrentDate() : Date {
        val calendar = Calendar.getInstance(Locale.getDefault())

        calendar.set(Calendar.HOUR, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        return calendar.time
    }
}