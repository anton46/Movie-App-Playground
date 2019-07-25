package com.movie.test.net.settings

class AppNetworkSettings : NetworkSettingsProvider {
    override fun getServerEnvirontment(): ServerEnvironment = ServerEnvironment.LIVE
}