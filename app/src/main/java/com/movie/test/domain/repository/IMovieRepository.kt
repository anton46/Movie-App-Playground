package com.movie.test.domain.repository

import com.movie.test.domain.data.response.MovieDetailResponse
import rx.Single
import com.movie.test.domain.data.response.MoviesResponse

interface IMovieRepository {
    fun fetchMovies(page: Int): Single<MoviesResponse>

    fun fetchMovieDetails(movieId: Int): Single<MovieDetailResponse>
}
