package com.movie.app.ui.details.mapper

import com.movie.app.extentions.shouldEqual
import com.movie.app.net.data.entity.Genre
import com.movie.app.net.data.entity.Language
import com.movie.app.net.data.response.MovieDetailResponse
import com.movie.app.net.factory.ImageUrlFactory
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieDetailsMapperTest {

    @Mock
    lateinit var imageUrlFactory: ImageUrlFactory

    @Test
    fun testMap() {
        `when`(imageUrlFactory.createBackdropUrl(anyString())).thenReturn("http://url/backdrop")
        val mapper = MovieDetailsMapper(imageUrlFactory)
        val movieDetailResponse = MovieDetailResponse(
            "title",
            "backdrop",
            listOf(Genre(1, "Action")),
            listOf(Language("en_US", "English")),
            "synopsis"
        )

        val result = mapper.map(movieDetailResponse)

        result.title shouldEqual "title"
        result.backdropUrl shouldEqual "http://url/backdrop"
        result.genres.size shouldEqual 1
        result.genres[0] shouldEqual "Action"
        result.languages[0] shouldEqual "English"
        result.synopsis shouldEqual "synopsis"
    }
}
