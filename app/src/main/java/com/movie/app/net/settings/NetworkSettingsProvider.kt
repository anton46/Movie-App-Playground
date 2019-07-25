package com.movie.app.net.settings

interface NetworkSettingsProvider {
    fun getServerEnvirontment(): ServerEnvironment
}