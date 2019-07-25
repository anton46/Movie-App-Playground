package com.movie.app

import com.movie.app.di.ApplicationComponent
import com.movie.app.di.DaggerTestMovieApplicationComponent
import com.movie.app.di.TestMovieApplicationComponent
import com.movie.app.net.TestNetworkSettingsModule

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