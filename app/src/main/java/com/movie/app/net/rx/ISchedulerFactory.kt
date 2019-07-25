package com.movie.app.net.rx

import rx.Scheduler

interface ISchedulerFactory {
    fun main(): Scheduler
    fun io(): Scheduler
    fun computation(): Scheduler
    fun trampoline(): Scheduler
}