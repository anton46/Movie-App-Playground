package com.movie.test

import com.movie.test.di.ApplicationComponent
import com.movie.test.di.DaggerTestMovieApplicationComponent
import com.movie.test.di.TestMovieApplicationComponent
import com.movie.test.net.TestNetworkSettingsModule

class EspressoApplication : MainApplication() {
    private lateinit var component: TestMovieApplicationComponent

    override fun inject() {
        component = DaggerTestMovieApplicationComponent.builder()
            .networkSettingsModule(TestNetworkSettingsModule())
            .build()
        component.inject(this)
    }

    override fun getApplicationComponent(): ApplicationComponent = component
}