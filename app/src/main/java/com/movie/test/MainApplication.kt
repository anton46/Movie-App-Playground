package com.movie.test

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.movie.test.di.*

open class MainApplication : Application(), HasApplicationComponent {

    private lateinit var component: MovieApplicationComponent

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        inject()
    }

    open fun inject() {
        component = DaggerMovieApplicationComponent.builder().build()
        component.inject(this)
    }

    override fun getApplicationComponent(): ApplicationComponent = component
}