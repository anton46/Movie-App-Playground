package com.movie.app.net.retrofit

import okhttp3.HttpUrl

interface IBaseUrlProvider {
    fun url(): HttpUrl
}