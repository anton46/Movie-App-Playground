package com.movie.app.net.retrofit

import com.movie.app.extentions.shouldEqual
import com.movie.app.net.settings.NetworkSettingsProvider
import com.movie.app.net.settings.ServerEnvironment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieApiBaseUrlTest {

    @Mock
    lateinit var networkSettingsProvider: NetworkSettingsProvider

    lateinit var apiUrl: AbstractBaseUrl

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
        `when`(networkSettingsProvider.getServerEnvironment()).thenReturn(ServerEnvironment.MOCK)

        apiUrl = FakeMovieBaseUrl(networkSettingsProvider)
        apiUrl.getHostForEnvironment(ServerEnvironment.MOCK) shouldEqual "localhost"
        apiUrl.url().port() shouldEqual 8080
        apiUrl.url().encodedPath() shouldEqual "/pathEnvironment/"
    }

    class FakeMovieBaseUrl(networkSettingsProvider: NetworkSettingsProvider) :
        AbstractBaseUrl(networkSettingsProvider) {

        override fun getHostForEnvironment(environment: ServerEnvironment?): String = when (environment) {
            ServerEnvironment.LIVE -> HOST
            else -> MOCK
        }

        override fun getPathForEnvironment(): String = "/pathEnvironment/"

        companion object {
            const val HOST = "api.themoviedb.org"
            const val MOCK = "localhost"
        }
    }
}
