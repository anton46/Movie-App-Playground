package com.movie.test.domain.repository

import com.movie.test.core.IDateTimeHelper
import com.movie.test.domain.api.MovieApi
import com.movie.test.domain.data.response.MovieDetailResponse
import com.movie.test.domain.data.response.MoviesResponse
import com.movie.test.net.rx.ISchedulerFactory
import com.movie.test.net.rx.transformer.RetryWithDelayTransformer
import rx.Single

class MovieRepository(
    private val movieApi: MovieApi,
    private val dateTImeHelper: IDateTimeHelper
) : IMovieRepository {

    override fun fetchMovies(page: Int): Single<MoviesResponse> = movieApi.fetchMovies(dateTImeHelper.getTodayDateString(), MIN_VOTE_COUNT, page)

    override fun fetchMovieDetails(movieId: Int): Single<MovieDetailResponse> = movieApi.fetchMovieDetails(movieId)

    companion object {
        private const val MIN_VOTE_COUNT = 50
    }
}