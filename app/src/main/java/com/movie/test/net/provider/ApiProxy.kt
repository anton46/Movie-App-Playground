package com.movie.test.net.provider

import rx.Observable
import rx.Scheduler
import com.movie.test.net.rx.transformer.RetryTransformers

open class ApiProxy<ApiInterface>(
    private val apiProvider: ApiProvider<ApiInterface>,
    private val computationScheduler: Scheduler
) {

    fun getApiInterface(): ApiInterface {
        return apiProvider.getApi()
    }

    fun <T> retryOnNetworkOrHttpError(observable: Observable<T>): Observable<T> {
        return observable
            .compose(RetryTransformers.retryOnNetworkOrHttpError(computationScheduler))
    }
}