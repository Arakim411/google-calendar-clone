package com.arakim.googlecalendarclone.util.kotlin

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime

fun String.toLocalDateTime(): LocalDateTime {
    val instant = Instant.parse(this)
    return LocalDateTime.ofInstant(instant, java.time.ZoneId.systemDefault())
}

fun LocalDate.toRfc3399(): String {
    return this.atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant().toString()
}