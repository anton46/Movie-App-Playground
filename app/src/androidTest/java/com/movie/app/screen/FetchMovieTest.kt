package com.movie.app.screen

import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.agoda.kakao.screen.Screen
import com.movie.app.mock.MockResponseServerDispatcher
import com.movie.app.page.HomeScreen
import com.movie.app.page.MovieDetailScreen
import com.movie.app.ui.home.HomeActivity
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class FetchMovieTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(HomeActivity::class.java, false, false)

    private val mockWebServer = MockWebServer()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        mockWebServer.start(8080)
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testFetchMovies() {
        mockWebServer.setDispatcher(MockResponseServerDispatcher())
        activityRule.launchActivity(Intent())

        Screen.onScreen<HomeScreen> {
            movies {
                isVisible()
                firstChild<HomeScreen.MovieItem> {
                    title {
                        isVisible()
                        hasText("Secret Obsession")
                    }
                    popularity{
                        isVisible()
                        hasText("Popularity : 83.0")
                    }
                    click()
                }
            }
        }

        Screen.onScreen<MovieDetailScreen> {
            synopsis {
                isVisible()
            }
            bookButton {
                isVisible()
            }
        }
    }
}
