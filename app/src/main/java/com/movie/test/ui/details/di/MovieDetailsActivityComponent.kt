package com.movie.test.ui.details.di

import com.movie.test.ui.details.MovieDetailsActivity
import com.movie.test.ui.di.ActivityComponent
import dagger.Subcomponent

@Subcomponent(modules = [MovieDetailsActivityModule::class])
interface MovieDetailsActivityComponent : ActivityComponent {
    fun inject(detailsActivity: MovieDetailsActivity)
}