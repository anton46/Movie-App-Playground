package com.movie.app.net.retrofit

import okhttp3.HttpUrl
import com.movie.app.net.settings.NetworkSettingsProvider
import com.movie.app.net.settings.ServerEnvironment

abstract class AbstractBaseUrl(networkSettingsProvider: NetworkSettingsProvider) : IBaseUrlProvider {

    private val url: HttpUrl

    init {
        val builder = HttpUrl.Builder()

        builder.scheme("http")
        builder.host(getHostForEnvironment(networkSettingsProvider.getServerEnvirontment()))

        if (networkSettingsProvider.getServerEnvirontment() == ServerEnvironment.MOCK) {
            builder.port(8080)
        }

        getPathForEnvironment()?.let {
            builder.encodedPath(getPathForEnvironment())
        }
        // terminate path with a "/" so the API endpoint paths are appended at the end
        builder.addPathSegment("")

        url = builder.build()
    }

    override fun url(): HttpUrl {
        return url
    }

    abstract fun getHostForEnvironment(environment: ServerEnvironment): String

    abstract fun getPathForEnvironment(): String?
}