package com.movie.app.domain.repository

import com.movie.app.core.IDateTimeHelper
import com.movie.app.domain.api.MovieApi
import com.movie.app.domain.data.response.MovieDetailResponse
import com.movie.app.domain.data.response.MoviesResponse
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