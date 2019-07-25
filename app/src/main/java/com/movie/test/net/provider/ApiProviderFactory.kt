package com.movie.test.net.provider

import com.movie.test.net.factory.ApiFactory

interface ApiProviderFactory {
    fun <Api> create(apiFactory: ApiFactory<Api>): ApiProvider<Api>
}