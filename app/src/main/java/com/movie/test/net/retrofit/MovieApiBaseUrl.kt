package com.movie.test.net.retrofit

import com.movie.test.net.settings.NetworkSettingsProvider
import com.movie.test.net.settings.ServerEnvironment

class MovieApiBaseUrl(networkSettingsProvider: NetworkSettingsProvider) : AbstractBaseUrl(networkSettingsProvider) {

    override fun getHostForEnvironment(environment: ServerEnvironment): String = when (environment) {
        ServerEnvironment.LIVE -> HOST
        else -> MOCK
    }

    override fun getPathForEnvironment(): String? = null

    companion object {
        const val HOST = "api.themoviedb.org"
        const val MOCK = "localhost"
    }
}
