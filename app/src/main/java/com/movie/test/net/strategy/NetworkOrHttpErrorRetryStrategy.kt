package com.movie.test.net.strategy

import java.util.concurrent.TimeUnit

/**
 * A default retry strategy. Performs 3 retries with 1 second delay.<br></br>
 * It assume that request does not require hard computation and possible errors are:<br></br>
 * - Network error<br></br>
 * - Transient problem with server
 */
class NetworkOrHttpErrorRetryStrategy : RetryStrategy {
    override val delay: Long
        get() = 1000L

    override val retries: Int
        get() = RetryStrategy.DEFAULT_RETRIES

    override val timeUnit: TimeUnit
        get() = TimeUnit.MILLISECONDS
}
