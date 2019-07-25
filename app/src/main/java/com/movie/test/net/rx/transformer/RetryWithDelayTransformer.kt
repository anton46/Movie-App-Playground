package com.movie.test.net.rx.transformer

import rx.Observable
import rx.Scheduler
import com.movie.test.net.rx.RetryWithDelay
import com.movie.test.net.strategy.DefaultRetryStrategy
import com.movie.test.net.strategy.RetryStrategy

class RetryWithDelayTransformer<T> @JvmOverloads constructor(
    private val retryStrategy: RetryStrategy = DefaultRetryStrategy(),
    private val scheduler: Scheduler
) : Observable.Transformer<T, T> {

    override fun call(tObservable: Observable<T>): Observable<T> {
        return tObservable.retryWhen(RetryWithDelay(retryStrategy, scheduler), scheduler)
    }
}
