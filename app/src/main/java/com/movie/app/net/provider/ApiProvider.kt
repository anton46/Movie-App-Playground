package com.movie.app.net.provider

interface ApiProvider<Api> {
    fun getApi(): Api
}