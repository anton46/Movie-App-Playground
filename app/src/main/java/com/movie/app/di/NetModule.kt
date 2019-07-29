package com.movie.app.di

import android.util.Log
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import rx.Scheduler
import com.movie.app.BuildConfig
import com.movie.app.domain.api.MovieApi
import com.movie.app.domain.proxy.MovieApiProxy
import com.movie.app.net.factory.ApiFactory
import com.movie.app.net.factory.GenericApiFactory
import com.movie.app.net.factory.ImageUrlFactory
import com.movie.app.net.factory.ImageUrlFactoryImpl
import com.movie.app.net.provider.ApiProvider
import com.movie.app.net.provider.ApiProviderFactory
import com.movie.app.net.provider.DefaultApiProvider
import com.movie.app.net.retrofit.GsonConverterFactory
import com.movie.app.net.retrofit.IBaseUrlProvider
import com.movie.app.net.retrofit.IBaseUrlProviderFactory
import com.movie.app.net.retrofit.MovieApiBaseUrl
import com.movie.app.net.settings.NetworkSettingsProvider
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
    fun providesImageUrlFactory(): ImageUrlFactory = ImageUrlFactoryImpl()

    companion object {
        private const val LOG_TAG = "HTTP"
        private const val WEATHER = "WEATHER"
    }
}