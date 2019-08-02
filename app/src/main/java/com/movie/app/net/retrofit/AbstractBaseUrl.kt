package com.movie.app.net.retrofit

import com.movie.app.net.settings.NetworkSettingsProvider
import com.movie.app.net.settings.ServerEnvironment
import okhttp3.HttpUrl

abstract class AbstractBaseUrl(networkSettingsProvider: NetworkSettingsProvider) : IBaseUrlProvider {

    private val url: HttpUrl

    init {
        val builder = HttpUrl.Builder()

        builder.scheme("http")
        builder.host(this.getHostForEnvironment(networkSettingsProvider.getServerEnvironment()))

        if (networkSettingsProvider.getServerEnvironment() == ServerEnvironment.MOCK) {
            builder.port(8080)
        }

        this.getPathForEnvironment()?.let {
            builder.encodedPath(it)
        }
        // terminate path with a "/" so the API endpoint paths are appended at the end
        builder.addPathSegment("")

        url = builder.build()
    }

    override fun url(): HttpUrl {
        return url
    }

    abstract fun getHostForEnvironment(environment: ServerEnvironment?): String

    abstract fun getPathForEnvironment(): String?
}