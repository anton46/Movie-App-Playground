package com.movie.test.ui.details

import com.movie.test.domain.data.entity.Genre
import com.movie.test.domain.data.entity.Language
import com.movie.test.domain.data.entity.Movie
import com.movie.test.domain.data.response.MovieDetailResponse
import com.movie.test.domain.data.response.MoviesResponse
import com.movie.test.domain.repository.IMovieRepository
import com.movie.test.net.rx.ISchedulerFactory
import com.movie.test.ui.details.mapper.IMovieDetailsMapper
import com.movie.test.ui.details.model.MovieDetailsViewModel
import com.movie.test.ui.home.mapper.IHomeViewModelMapper
import com.movie.test.ui.home.model.HomeViewModel
import com.movie.test.ui.home.model.MovieViewModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import rx.Single
import rx.schedulers.TestScheduler
import java.math.BigInteger


@RunWith(MockitoJUnitRunner::class)
class MovieDetailsPresenterTest {

    private val testScheduler = TestScheduler()

    @Mock
    lateinit var movieRepository: IMovieRepository

    @Mock
    lateinit var schedulerFactory: ISchedulerFactory

    @Mock
    lateinit var mapper: IMovieDetailsMapper

    @Mock
    lateinit var view: MovieDetailsView

    lateinit var presenter: MovieDetailsPresenter

    @Before
    fun setUp() {
        presenter = MovieDetailsPresenter(movieRepository, schedulerFactory, mapper)
        presenter.attachView(view)

        `when`(schedulerFactory.io()).thenReturn(testScheduler)
        `when`(schedulerFactory.main()).thenReturn(testScheduler)
    }

    @Test
    fun `test load on success flow`() {
        val expectedResponse = MovieDetailResponse(
            "title",
            "url",
            listOf(Genre(1, "genre-1")),
            listOf(Language("en", "English")),
            "Synopsis"
        )

        val expectedViewModel = MovieDetailsViewModel(
            "title",
            "url",
            "Synopsis",
            listOf("English"),
            listOf("genre-1")
        )

        `when`(movieRepository.fetchMovieDetails(1)).thenReturn(Single.just(expectedResponse))
        `when`(mapper.map(expectedResponse)).thenReturn(expectedViewModel)

        presenter.fetchDetails(1)
        testScheduler.triggerActions()

        verify(view).showLoading()
        verify(movieRepository).fetchMovieDetails(1)
        verify(view).showContent(expectedViewModel)
        val a = BigInteger("123456")
    }

    @Test
    fun `test load on error flow`() {
        `when`(movieRepository.fetchMovieDetails(1)).thenReturn(Single.error(Exception()))

        presenter.fetchDetails(1)
        testScheduler.triggerActions()

        verify(view).showLoading()
        verify(movieRepository).fetchMovieDetails(1)
        verify(view).showError()
    }
}