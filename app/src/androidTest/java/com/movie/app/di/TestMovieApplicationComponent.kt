package com.movie.app.di

import com.movie.app.EspressoApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetModule::class, RxModule::class, NetworkSettingsModule::class, DateModule::class])
interface TestMovieApplicationComponent : MovieApplicationComponent {
    fun inject(application: EspressoApplication)
}