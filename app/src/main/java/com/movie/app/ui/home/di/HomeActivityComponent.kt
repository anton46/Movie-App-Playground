package com.movie.app.ui.home.di

import com.movie.app.ui.di.ActivityComponent
import dagger.Subcomponent
import com.movie.app.ui.home.HomeActivity

@Subcomponent(modules = [HomeActivityModule::class])
interface HomeActivityComponent : ActivityComponent {
    fun inject(homeActivity: HomeActivity)
}