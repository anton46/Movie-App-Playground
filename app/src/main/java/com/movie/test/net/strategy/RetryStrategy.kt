package com.movie.test.net.strategy

import java.util.concurrent.TimeUnit

interface RetryStrategy {

    val delay: Long

    val retries: Int

    val timeUnit: TimeUnit

    companion object {
        val DEFAULT_DELAY = 3000L
        val DEFAULT_RETRIES = 3
        val DEFAULT_TIME_UNIT = TimeUnit.MILLISECONDS
    }
}
