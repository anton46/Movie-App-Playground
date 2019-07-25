package com.movie.app.net

import com.movie.app.di.NetworkSettingsModule
import com.movie.app.net.settings.NetworkSettingsProvider

class TestNetworkSettingsModule : NetworkSettingsModule() {

    override fun provideNetworkSettingsProvider(): NetworkSettingsProvider = TestAutomationNetworkSettings()
}