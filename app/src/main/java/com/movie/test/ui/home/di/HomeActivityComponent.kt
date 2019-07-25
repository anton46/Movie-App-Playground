package com.movie.test.ui.home.di

import com.movie.test.ui.di.ActivityComponent
import dagger.Subcomponent
import com.movie.test.ui.home.HomeActivity

@Subcomponent(modules = [HomeActivityModule::class])
interface HomeActivityComponent : ActivityComponent {
    fun inject(homeActivity: HomeActivity)
}