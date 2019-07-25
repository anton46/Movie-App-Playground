package com.movie.test.ui.details.di

import com.movie.test.core.IDateTimeHelper
import com.movie.test.domain.api.MovieApi
import com.movie.test.domain.repository.IMovieRepository
import com.movie.test.domain.repository.MovieRepository
import com.movie.test.net.factory.ImageUrlFactory
import com.movie.test.net.rx.ISchedulerFactory
import com.movie.test.ui.details.MovieDetailsPresenter
import com.movie.test.ui.details.mapper.IMovieDetailsMapper
import com.movie.test.ui.details.mapper.MovieDetailsMapper
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
