package com.movie.app.di

import dagger.Component
import com.movie.app.MainApplication
import com.movie.app.ui.details.di.MovieDetailsActivityComponent
import com.movie.app.ui.details.di.MovieDetailsActivityModule
import com.movie.app.ui.home.di.HomeActivityComponent
import com.movie.app.ui.home.di.HomeActivityModule
import javax.inject.Singleton

@Singleton
@Component(modules = [NetModule::class, RxModule::class, NetworkSettingsModule::class, DateModule::class])
interface MovieApplicationComponent : ApplicationComponent {
    fun inject(mainApplication: MainApplication)

    fun add(module: HomeActivityModule): HomeActivityComponent

    fun add(module: MovieDetailsActivityModule): MovieDetailsActivityComponent
}
