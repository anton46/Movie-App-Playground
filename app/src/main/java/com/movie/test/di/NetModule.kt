package com.movie.test.di

import android.util.Log
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import rx.Scheduler
import com.movie.test.BuildConfig
import com.movie.test.domain.api.MovieApi
import com.movie.test.domain.proxy.MovieApiProxy
import com.movie.test.net.factory.ApiFactory
import com.movie.test.net.factory.GenericApiFactory
import com.movie.test.net.factory.ImageUrlFactory
import com.movie.test.net.provider.ApiProvider
import com.movie.test.net.provider.ApiProviderFactory
import com.movie.test.net.provider.DefaultApiProvider
import com.movie.test.net.retrofit.GsonConverterFactory
import com.movie.test.net.retrofit.IBaseUrlProvider
import com.movie.test.net.retrofit.IBaseUrlProviderFactory
import com.movie.test.net.retrofit.MovieApiBaseUrl
import com.movie.test.net.settings.NetworkSettingsProvider
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideOkHttpClientBuilder() = OkHttpClient.Builder()

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor { Log.d(LOG_TAG, it) }

        httpLoggingInterceptor.level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE

        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        builder: OkHttpClient.Builder,
        interceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return builder
            .addNetworkInterceptor(interceptor)
            .addInterceptor(interceptor)
            .retryOnConnectionFailure(false)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(
        gson: Gson
    ): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Singleton
    @Provides
    fun provideApiProviderFactory(): ApiProviderFactory {
        return object : ApiProviderFactory {
            override fun <Api> create(apiFactory: ApiFactory<Api>): ApiProvider<Api> {
                return DefaultApiProvider(apiFactory.create())
            }
        }
    }

    @Singleton
    @Provides
    fun provideWeatherAPI(
        apiFactory: ApiFactory<MovieApi>,
        apiProviderFactory: ApiProviderFactory,
        @Named(RxModule.COMPUTATION) schedulerFactory: Scheduler
    ): MovieApi {
        return MovieApiProxy(apiProviderFactory.create(apiFactory), schedulerFactory)
    }

    @Singleton
    @Provides
    @Named(WEATHER)
    fun provideWeatherApiBaseUrl(networkSettingsProvider: NetworkSettingsProvider): IBaseUrlProviderFactory {
        return object : IBaseUrlProviderFactory {
            override fun create(): IBaseUrlProvider {
                return MovieApiBaseUrl(networkSettingsProvider)
            }
        }
    }

    @Singleton
    @Provides
    fun provideWeatherApiFactory(
        client: OkHttpClient,
        @Named(WEATHER) urlProviderFactory: IBaseUrlProviderFactory,
        converterFactory: GsonConverterFactory
    ): ApiFactory<MovieApi> {
        return GenericApiFactory(
            client,
            urlProviderFactory,
            converterFactory,
            MovieApi::class.java
        )
    }

    @Singleton
    @Provides
    fun providesImageUrlFactory(): ImageUrlFactory = ImageUrlFactory()

    companion object {
        private const val LOG_TAG = "HTTP"
        private const val WEATHER = "WEATHER"
    }
}