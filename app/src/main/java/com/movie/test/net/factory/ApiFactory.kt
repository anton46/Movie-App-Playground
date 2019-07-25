package com.movie.test.net.factory

interface ApiFactory<Api> {
    fun create(): Api
}
