package com.movie.test.domain.proxy

import com.movie.test.domain.api.MovieApi
import com.movie.test.domain.data.response.MovieDetailResponse
import com.movie.test.domain.data.response.MoviesResponse
import com.movie.test.net.provider.ApiProvider
import com.movie.test.net.provider.ApiProxy
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
