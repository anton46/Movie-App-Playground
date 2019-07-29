package com.movie.app.net.factory

interface ImageUrlFactory {
    fun createPosterUrl(path : String) : String
    fun createBackdropUrl(path : String) : String
}