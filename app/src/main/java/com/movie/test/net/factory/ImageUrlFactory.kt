package com.movie.test.net.factory

class ImageUrlFactory {
    companion object {
        const val POSTER_BASE_URL = "http://image.tmdb.org/t/p/w342/"
        const val BACKDROP_BASE_URL = "http://image.tmdb.org/t/p/w780/"
    }

    fun createPosterUrl(remoteFileName: String): String = POSTER_BASE_URL + remoteFileName

    fun createBackdropUrl(remoteFileName: String): String = BACKDROP_BASE_URL + remoteFileName
}