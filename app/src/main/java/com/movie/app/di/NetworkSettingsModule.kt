package com.movie.app.di

import dagger.Module
import dagger.Provides
import com.movie.app.net.settings.AppNetworkSettings
import com.movie.app.net.settings.NetworkSettingsProvider
import javax.inject.Singleton

@Module
open class NetworkSettingsModule {

    @Provides
    @Singleton
    open fun provideNetworkSettingsProvider(): NetworkSettingsProvider = AppNetworkSettings()

}