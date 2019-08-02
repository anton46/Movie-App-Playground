package com.movie.app.net.settings

interface NetworkSettingsProvider {
    fun getServerEnvironment(): ServerEnvironment
}