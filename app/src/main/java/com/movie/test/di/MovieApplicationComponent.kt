package com.movie.test.di

import dagger.Component
import com.movie.test.MainApplication
import com.movie.test.ui.details.di.MovieDetailsActivityComponent
import com.movie.test.ui.details.di.MovieDetailsActivityModule
import com.movie.test.ui.home.di.HomeActivityComponent
import com.movie.test.ui.home.di.HomeActivityModule
import javax.inject.Singleton

@Singleton
@Component(modules = [NetModule::class, RxModule::class, NetworkSettingsModule::class, DateModule::class])
interface MovieApplicationComponent : ApplicationComponent {
    fun inject(mainApplication: MainApplication)

    fun add(module: HomeActivityModule): HomeActivityComponent

    fun add(module: MovieDetailsActivityModule): MovieDetailsActivityComponent
}
