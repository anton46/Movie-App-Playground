package com.movie.app.net.rx.transformer

import rx.Scheduler
import com.movie.app.net.strategy.NetworkOrHttpErrorRetryStrategy

/**
 * Class containing methods to create common operation retry transformers
 */
class RetryTransformers {

    /**
     * Fast retry strategy for http (error response code) and network errors.
     * More details about strategy see in: [NetworkOrHttpErrorRetryStrategy] <br></br>
     * <br></br>
     * **Notice!** To logically separate different retry behaviours use this transformers
     * <u>before</u>
     * [ CategoryFilteringTransformer][CategoryFilteringTransformer]
     * <br></br>
     *
     * @param computationScheduler A **computation** scheduler to perform retry with delay logic
     */
    companion object {
        private val NETWORK_OR_HTTP_ERROR_STRATEGY = NetworkOrHttpErrorRetryStrategy()

        fun <T> retryOnNetworkOrHttpError(computationScheduler: Scheduler): RetryWithDelayTransformer<T> {
            return RetryWithDelayTransformer(NETWORK_OR_HTTP_ERROR_STRATEGY, computationScheduler)
        }

    }
}