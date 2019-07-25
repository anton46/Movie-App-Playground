package com.movie.test.net

import com.movie.test.di.NetworkSettingsModule
import com.movie.test.net.settings.NetworkSettingsProvider

class TestNetworkSettingsModule : NetworkSettingsModule() {

    override fun provideNetworkSettingsProvider(): NetworkSettingsProvider = TestAutomationNetworkSettings()
}