package com.movie.test.di

import dagger.Module
import dagger.Provides
import com.movie.test.net.settings.AppNetworkSettings
import com.movie.test.net.settings.NetworkSettingsProvider
import javax.inject.Singleton

@Module
open class NetworkSettingsModule {

    @Provides
    @Singleton
    open fun provideNetworkSettingsProvider(): NetworkSettingsProvider = AppNetworkSettings()

}