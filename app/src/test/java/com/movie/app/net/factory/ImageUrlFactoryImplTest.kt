package com.movie.app.net.factory

import com.movie.app.extentions.shouldEqual
import org.junit.Test

class ImageUrlFactoryImplTest {

    @Test
    fun testCreateUrl() {
        val imageUrlFactory = ImageUrlFactoryImpl()

        imageUrlFactory.createPosterUrl("poster") shouldEqual "http://image.tmdb.org/t/p/w342/poster"
        imageUrlFactory.createBackdropUrl("backdrop") shouldEqual "http://image.tmdb.org/t/p/w780/backdrop"
    }
}