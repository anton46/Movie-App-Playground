package com.movie.app.net.factory

interface ApiFactory<Api> {
    fun create(): Api
}
