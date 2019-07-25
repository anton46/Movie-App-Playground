package com.movie.test.ui.home

import com.movie.test.domain.data.entity.Movie
import com.movie.test.domain.data.response.MoviesResponse
import com.movie.test.domain.repository.IMovieRepository
import com.movie.test.net.rx.ISchedulerFactory
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


@RunWith(MockitoJUnitRunner::class)
class HomePresenterTest {

    private val testScheduler = TestScheduler()

    @Mock
    lateinit var movieRepository: IMovieRepository

    @Mock
    lateinit var schedulerFactory: ISchedulerFactory

    @Mock
    lateinit var mapper: IHomeViewModelMapper

    @Mock
    lateinit var view: HomeView

    lateinit var presenter: HomePresenter

    @Before
    fun setUp() {
        presenter = HomePresenter(movieRepository, schedulerFactory, mapper)
        presenter.attachView(view)

        `when`(schedulerFactory.io()).thenReturn(testScheduler)
        `when`(schedulerFactory.main()).thenReturn(testScheduler)
    }

    @Test
    fun `test load on success flow`() {
        val expectedResponse = MoviesResponse(listOf(Movie(1, "url", "title", 10.0f)))
        val expectedViewModel = HomeViewModel(listOf(MovieViewModel(1, "url", "title", 10.0f)))

        `when`(movieRepository.fetchMovies(1)).thenReturn(Single.just(expectedResponse))
        `when`(mapper.map(expectedResponse)).thenReturn(expectedViewModel)

        presenter.load()
        testScheduler.triggerActions()

        verify(view).showLoading()
        verify(view, never()).onLoadMore()
        verify(movieRepository).fetchMovies(1)
        verify(view).showContent(expectedViewModel)
    }

    @Test
    fun `test load more on success flow`() {
        val expectedLoadMoreResponse = MoviesResponse(listOf(Movie(2, "url-2", "title-2", 20.0f)))
        val expectedLoadMoreViewModel = HomeViewModel(listOf(MovieViewModel(2, "url-2", "title-2", 20.0f)))

        `when`(movieRepository.fetchMovies(2)).thenReturn(Single.just(expectedLoadMoreResponse))
        `when`(mapper.map(expectedLoadMoreResponse)).thenReturn(expectedLoadMoreViewModel)

        presenter.nextPage()
        testScheduler.triggerActions()

        verify(view, never()).showLoading()
        verify(view).onLoadMore()
        verify(movieRepository).fetchMovies(2)
        verify(view).showContent(expectedLoadMoreViewModel)
    }


    @Test
    fun `test load on error flow`() {
        `when`(movieRepository.fetchMovies(1)).thenReturn(Single.error(Exception()))

        presenter.load()
        testScheduler.triggerActions()

        verify(view).showLoading()
        verify(movieRepository).fetchMovies(1)
        verify(view).showError()
    }
}