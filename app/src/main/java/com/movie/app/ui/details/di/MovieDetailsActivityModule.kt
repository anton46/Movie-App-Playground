package com.movie.app.ui.details.di

import com.movie.app.core.IDateTimeHelper
import com.movie.app.domain.api.MovieApi
import com.movie.app.domain.repository.IMovieRepository
import com.movie.app.domain.repository.MovieRepository
import com.movie.app.net.factory.ImageUrlFactory
import com.movie.app.net.rx.ISchedulerFactory
import com.movie.app.ui.details.MovieDetailsPresenter
import com.movie.app.ui.details.mapper.IMovieDetailsMapper
import com.movie.app.ui.details.mapper.MovieDetailsMapper
import dagger.Module
import dagger.Provides

@Module
class MovieDetailsActivityModule {

    @Provides
    fun providesMovieRepository(
        movieApi: MovieApi,
        dateTimeHelper: IDateTimeHelper
    ): IMovieRepository = MovieRepository(movieApi, dateTimeHelper)

    @Provides
    fun providesMovieDetailsPresenter(
        repository: IMovieRepository,
        schedulerFactory: ISchedulerFactory,
        mapper: IMovieDetailsMapper
    ): MovieDetailsPresenter = MovieDetailsPresenter(repository, schedulerFactory, mapper)

    @Provides
    fun providesMovieDetailsViewModelMapper(imageUrlFactory: ImageUrlFactory): IMovieDetailsMapper =
        MovieDetailsMapper(imageUrlFactory)
}
