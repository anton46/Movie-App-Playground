package com.movie.test.net.retrofit

import okhttp3.HttpUrl

interface IBaseUrlProvider {
    fun url(): HttpUrl
}