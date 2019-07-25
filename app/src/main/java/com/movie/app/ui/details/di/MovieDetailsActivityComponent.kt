package com.movie.app.ui.details.di

import com.movie.app.ui.details.MovieDetailsActivity
import com.movie.app.ui.di.ActivityComponent
import dagger.Subcomponent

@Subcomponent(modules = [MovieDetailsActivityModule::class])
interface MovieDetailsActivityComponent : ActivityComponent {
    fun inject(detailsActivity: MovieDetailsActivity)
}