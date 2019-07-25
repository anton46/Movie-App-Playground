package com.movie.test.net.rx

import rx.Observable
import rx.Scheduler
import rx.functions.Func1
import com.movie.test.net.strategy.RetryStrategy

class RetryWithDelay(
    private val retryStrategy: RetryStrategy,
    private val scheduler: Scheduler
) :
    Func1<Observable<out Throwable>, Observable<*>> {

    private var retryCount: Int = 0

    override fun call(attempts: Observable<out Throwable>): Observable<*> {
        return attempts.flatMap<Any> { throwable ->
            if (retryCount++ < retryStrategy.retries)
                Observable.timer(retryStrategy.delay, retryStrategy.timeUnit, scheduler)
            else Observable.error(throwable)
        }
    }
}