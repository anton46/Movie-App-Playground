package com.movie.app.domain.proxy

import com.movie.app.domain.api.MovieApi
import com.movie.app.domain.data.response.MovieDetailResponse
import com.movie.app.domain.data.response.MoviesResponse
import com.movie.app.net.provider.ApiProvider
import com.movie.app.net.provider.ApiProxy
import rx.Scheduler
import rx.Single

class MovieApiProxy(
    apiProvider: ApiProvider<MovieApi>,
    scheduler: Scheduler
) : ApiProxy<MovieApi>(apiProvider, scheduler), MovieApi {

    override fun fetchMovies(requestDate: String, minVoteCount: Int, days: Int): Single<MoviesResponse> {
        return getApiInterface().fetchMovies(requestDate, minVoteCount, days)
    }

    override fun fetchMovieDetails(movieId: Int): Single<MovieDetailResponse> {
        return getApiInterface().fetchMovieDetails(movieId)
    }
}
