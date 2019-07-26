package com.movie.app.domain.api

import com.movie.app.net.data.response.MovieDetailResponse
import retrofit2.http.GET
import retrofit2.http.Query
import com.movie.app.net.data.response.MoviesResponse
import retrofit2.http.Path
import rx.Single

interface MovieApi {

    companion object {
        const val VERSION = "3"
        const val API_KEY = "328c283cd27bd1877d9080ccb1604c91"
        const val PATH_DISCOVER = "discover/movie"
        const val PATH_MOVIE_DETAIL = "movie"
        const val MOVIE_ID = "movieId"
    }

    @GET("$VERSION/$PATH_DISCOVER?api_key=$API_KEY&sort_by=release_date.desc")
    fun fetchMovies(
        @Query("primary_release_date.lte") requestDate: String,
        @Query("vote_count.gte") minVoteCount: Int,
        @Query("page") days: Int
    ): Single<MoviesResponse>

    @GET("$VERSION/$PATH_MOVIE_DETAIL/{$MOVIE_ID}?api_key=$API_KEY")
    fun fetchMovieDetails(
        @Path(MOVIE_ID) movieId: Int
    ): Single<MovieDetailResponse>
}
