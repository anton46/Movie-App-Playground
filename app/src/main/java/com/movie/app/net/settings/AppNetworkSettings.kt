package com.movie.app.net.settings

class AppNetworkSettings : NetworkSettingsProvider {
    override fun getServerEnvirontment(): ServerEnvironment = ServerEnvironment.LIVE
}