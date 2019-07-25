package com.movie.app.ui.home.di

import com.movie.app.core.IDateTimeHelper
import com.movie.app.domain.api.MovieApi
import com.movie.app.domain.repository.IMovieRepository
import com.movie.app.domain.repository.MovieRepository
import com.movie.app.net.factory.ImageUrlFactory
import com.movie.app.net.rx.ISchedulerFactory
import com.movie.app.ui.home.HomeActivity
import com.movie.app.ui.home.HomePresenter
import com.movie.app.ui.home.MoviesAdapter
import com.movie.app.ui.home.mapper.HomeViewModelMapper
import com.movie.app.ui.home.mapper.IHomeViewModelMapper
import dagger.Module
import dagger.Provides

@Module
class HomeActivityModule(private val homeActivity: HomeActivity) {

    @Provides
    fun providesWeatherRepository(
        movieApi: MovieApi,
        dateTimeHelper: IDateTimeHelper
    ): IMovieRepository = MovieRepository(movieApi, dateTimeHelper)

    @Provides
    fun providesHomePresenter(
        repository: IMovieRepository,
        schedulerFactory: ISchedulerFactory,
        mapper: IHomeViewModelMapper
    ): HomePresenter = HomePresenter(repository, schedulerFactory, mapper)

    @Provides
    fun providesHomeViewModelMapper(imageUrlFactory: ImageUrlFactory): IHomeViewModelMapper =
        HomeViewModelMapper(imageUrlFactory)

    @Provides
    fun providesAdapter(): MoviesAdapter = MoviesAdapter(homeActivity)
}