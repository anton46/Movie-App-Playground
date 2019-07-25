package com.movie.test.net.factory

import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import com.movie.test.net.retrofit.IBaseUrlProviderFactory

class GenericApiFactory<Api>(
    private val client: OkHttpClient,
    private val urlProviderFactory: IBaseUrlProviderFactory,
    private val converterFactory: Converter.Factory?,
    private val interfaceClass: Class<Api>
) : ApiFactory<Api> {

    override fun create(): Api {
        val retrofit = createRetrofit()
        return retrofit.create<Api>(interfaceClass)
    }

    private fun createRetrofit(): Retrofit {
        val baseUrlProvider = urlProviderFactory.create()

        val builder = Retrofit.Builder()
        if (converterFactory != null) {
            builder.addConverterFactory(converterFactory)
        }

        return builder
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(client)
            .baseUrl(baseUrlProvider.url())
            .build()
    }
}
