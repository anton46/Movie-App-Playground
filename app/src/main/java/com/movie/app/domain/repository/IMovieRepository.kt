package com.movie.app.domain.repository

import com.movie.app.net.data.response.MovieDetailResponse
import rx.Single
import com.movie.app.net.data.response.MoviesResponse

interface IMovieRepository {
    fun fetchMovies(page: Int): Single<MoviesResponse>

    fun fetchMovieDetails(movieId: Int): Single<MovieDetailResponse>
}
