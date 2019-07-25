package com.movie.test.net.settings

interface NetworkSettingsProvider {
    fun getServerEnvirontment(): ServerEnvironment
}