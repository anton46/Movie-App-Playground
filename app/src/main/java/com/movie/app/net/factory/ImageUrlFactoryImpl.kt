package com.movie.app.net.factory

class ImageUrlFactoryImpl : ImageUrlFactory {
    companion object {
        const val POSTER_BASE_URL = "http://image.tmdb.org/t/p/w342/"
        const val BACKDROP_BASE_URL = "http://image.tmdb.org/t/p/w780/"
    }

    override fun createPosterUrl(path: String): String = POSTER_BASE_URL + path

    override fun createBackdropUrl(path: String): String = BACKDROP_BASE_URL + path
}
