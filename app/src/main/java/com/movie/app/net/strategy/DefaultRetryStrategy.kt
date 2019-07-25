package com.movie.app.net.strategy

import java.util.concurrent.TimeUnit

internal class DefaultRetryStrategy : RetryStrategy {

    override val delay: Long
        get() = RetryStrategy.DEFAULT_DELAY

    override val retries: Int
        get() = RetryStrategy.DEFAULT_RETRIES

    override val timeUnit: TimeUnit
        get() = RetryStrategy.DEFAULT_TIME_UNIT
}
 