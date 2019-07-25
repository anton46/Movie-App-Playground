package com.movie.app.net.provider

import com.movie.app.net.factory.ApiFactory

interface ApiProviderFactory {
    fun <Api> create(apiFactory: ApiFactory<Api>): ApiProvider<Api>
}