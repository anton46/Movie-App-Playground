package com.movie.app.net.provider

class DefaultApiProvider<Api>(private val api: Api) : ApiProvider<Api> {
    override fun getApi(): Api = api
}
