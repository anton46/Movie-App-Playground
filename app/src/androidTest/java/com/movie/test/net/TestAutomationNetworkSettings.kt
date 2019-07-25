package com.movie.test.net

import com.movie.test.net.settings.NetworkSettingsProvider
import com.movie.test.net.settings.ServerEnvironment

class TestAutomationNetworkSettings : NetworkSettingsProvider {
    override fun getServerEnvirontment(): ServerEnvironment = ServerEnvironment.MOCK
}