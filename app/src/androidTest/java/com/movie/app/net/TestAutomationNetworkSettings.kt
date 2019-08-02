package com.movie.app.net

import com.movie.app.net.settings.NetworkSettingsProvider
import com.movie.app.net.settings.ServerEnvironment

class TestAutomationNetworkSettings : NetworkSettingsProvider {
    override fun getServerEnvironment(): ServerEnvironment = ServerEnvironment.MOCK
}