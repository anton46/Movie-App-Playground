package com.movie.app.core

import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter

class DateTimeHelper : IDateTimeHelper {
    override fun getTodayDateString(): String = OffsetDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
}