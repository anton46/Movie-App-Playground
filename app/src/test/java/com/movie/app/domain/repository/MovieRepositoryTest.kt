package com.movie.app.domain.repository

import com.movie.app.core.IDateTimeHelper
import com.movie.app.domain.api.MovieApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryTest {

    @Mock
    lateinit var movieApi: MovieApi

    @Mock
    lateinit var dateTimeHelperFactory: IDateTimeHelper

    lateinit var repository: MovieRepository

    @Before
    fun setUp() {
        repository = MovieRepository(movieApi, dateTimeHelperFactory)
    }

    @Test
    fun testFetchMovies() {
        `when`(dateTimeHelperFactory.getTodayDateString()).thenReturn("1/1/2019")
        repository.fetchMovies(100)

        verify(movieApi).fetchMovies("1/1/2019", 50, 100)
    }

    @Test
    fun testFetchMoviesDetails() {
        repository.fetchMovieDetails(100)

        verify(movieApi).fetchMovieDetails(100)
    }
}