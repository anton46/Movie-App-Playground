package com.movie.test.ui.home.di

import com.movie.test.core.IDateTimeHelper
import com.movie.test.domain.api.MovieApi
import com.movie.test.domain.repository.IMovieRepository
import com.movie.test.domain.repository.MovieRepository
import com.movie.test.net.factory.ImageUrlFactory
import com.movie.test.net.rx.ISchedulerFactory
import com.movie.test.ui.home.HomeActivity
import com.movie.test.ui.home.HomePresenter
import com.movie.test.ui.home.MoviesAdapter
import com.movie.test.ui.home.mapper.HomeViewModelMapper
import com.movie.test.ui.home.mapper.IHomeViewModelMapper
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