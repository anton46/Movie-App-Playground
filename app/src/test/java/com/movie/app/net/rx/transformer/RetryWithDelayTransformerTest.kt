package com.movie.app.net.rx.transformer

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import rx.Observable
import rx.Observer
import rx.schedulers.TestScheduler
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class RetryWithDelayTransformerTest {

    @Mock
    lateinit var observer: Observer<Any>

    lateinit var scheduler: TestScheduler

    @Before
    @Throws(Exception::class)
    fun setUp() {
        scheduler = TestScheduler()
    }

    @Test
    fun testDefaults() {
        val source = Observable.error<Any>(RuntimeException()).repeat(scheduler)
        val delayed = source.compose(RetryWithDelayTransformer(scheduler = scheduler))
        delayed.subscribe(observer)

        scheduler.advanceTimeTo(8999, TimeUnit.MILLISECONDS)
        verify(observer, never()).onNext(any(RuntimeException::class.java))
        verify(observer, never()).onCompleted()
        verify(observer, never()).onError(any(Throwable::class.java))

        scheduler.advanceTimeTo(9001, TimeUnit.MILLISECONDS)
        verify(observer, never()).onNext(any(RuntimeException::class.java))
        verify(observer, never()).onCompleted()
        verify(observer).onError(any(Throwable::class.java))
    }

    @Test
    fun testExplicitTiming() {
        val source = Observable.error<Any>(RuntimeException()).repeat(scheduler)
        val delayed = source.compose(
            RetryWithDelayTransformer(
                scheduler = scheduler
            )
        )
        delayed.subscribe(observer)

        scheduler.advanceTimeTo(8999, TimeUnit.MILLISECONDS)
        verify(observer, never()).onNext(any(RuntimeException::class.java))
        verify(observer, never()).onCompleted()
        verify(observer, never()).onError(any(Throwable::class.java))

        scheduler.advanceTimeTo(9001, TimeUnit.MILLISECONDS)
        verify(observer, never()).onNext(any(RuntimeException::class.java))
        verify(observer, never()).onCompleted()
        verify(observer).onError(any(Throwable::class.java))
    }
}