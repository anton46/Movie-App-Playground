package com.movie.test.di

import com.movie.test.EspressoApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetModule::class, RxModule::class, NetworkSettingsModule::class, DateModule::class])
interface TestMovieApplicationComponent : MovieApplicationComponent {
    fun inject(application: EspressoApplication)
}