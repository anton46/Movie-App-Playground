package com.movie.app.ui.home.mapper

import com.movie.app.extentions.shouldEqual
import com.movie.app.net.data.entity.Movie
import com.movie.app.net.data.response.MoviesResponse
import com.movie.app.net.factory.ImageUrlFactory
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelMapperTest {

    @Mock
    lateinit var imageUrlFactory: ImageUrlFactory

    @Test
    fun testMap() {
        `when`(imageUrlFactory.createPosterUrl(anyString())).thenReturn("http://url/poster")
        val homeViewModelMapper = HomeViewModelMapper(imageUrlFactory)
        val moviesResponse = MoviesResponse(
            listOf(
                Movie(1, "poster", "Movie 1", 90.0f),
                Movie(2, null, "Movie 2", 8.0f)
            )
        )

        val result = homeViewModelMapper.map(moviesResponse)

        result.movies.size shouldEqual 1
        result.movies[0].movieId shouldEqual 1
        result.movies[0].posterUrl shouldEqual "http://url/poster"
        result.movies[0].popularity shouldEqual 90.0f
        result.movies[0].title shouldEqual "Movie 1"
    }
}
