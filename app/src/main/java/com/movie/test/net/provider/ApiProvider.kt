package com.movie.test.net.provider

interface ApiProvider<Api> {
    fun getApi(): Api
}