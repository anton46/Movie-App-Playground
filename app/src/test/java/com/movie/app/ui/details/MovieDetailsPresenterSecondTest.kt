package com.movie.app.ui.details

import com.movie.app.net.data.entity.Genre
import com.movie.app.net.data.entity.Language
import com.movie.app.net.data.response.MovieDetailResponse
import com.movie.app.domain.repository.IMovieRepository
import com.movie.app.net.rx.ISchedulerFactory
import com.movie.app.ui.details.mapper.IMovieDetailsMapper
import com.movie.app.ui.details.model.MovieDetailsViewModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import rx.Single
import rx.schedulers.TestScheduler


@RunWith(MockitoJUnitRunner::class)
class MovieDetailsPresenterSecondTest {

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
    fun `test load on error flow`() {
        `when`(movieRepository.fetchMovieDetails(2)).thenReturn(Single.error(Exception()))

        presenter.fetchDetails(2)
        testScheduler.triggerActions()

        verify(view).showLoading()
        verify(movieRepository).fetchMovieDetails(2)
        verify(view).showError()
    }
}
