package com.movie.app.net.retrofit

import com.movie.app.extentions.shouldEqual
import com.movie.app.net.settings.NetworkSettingsProvider
import com.movie.app.net.settings.ServerEnvironment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieApiBaseUrlTest {

    @Mock
    lateinit var networkSettingsProvider: NetworkSettingsProvider

    lateinit var apiUrl: MovieApiBaseUrl

    @Before
    fun test() {
        apiUrl = MovieApiBaseUrl(networkSettingsProvider)
    }

    @Test
    fun testGetHostLiveEnvironment() {
        apiUrl.getHostForEnvironment(ServerEnvironment.LIVE) shouldEqual "api.themoviedb.org"
    }

    @Test
    fun testGetHostMockEnvironment() {
        apiUrl.getHostForEnvironment(ServerEnvironment.MOCK) shouldEqual "localhost"
    }

    @Test
    fun testGetPathForEnvironment() {
        apiUrl.getPathForEnvironment() shouldEqual null
    }

}