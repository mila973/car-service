package com.mif.carservice.util

import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

class Time(val clock: Clock) {
    companion object {
        val DEFAULT_TIMEZONE: ZoneId = ZoneId.of("Europe/Vilnius")

        fun parse(dateTime: String): Instant = ZonedDateTime.parse(dateTime).toInstant()
        fun fixed(dateTime: String) = Time(Clock.fixed(parse(dateTime), DEFAULT_TIMEZONE))
        fun defaultTimezone() = Time(Clock.system(DEFAULT_TIMEZONE))
    }

    fun now(): Instant = Instant.now(clock)
}