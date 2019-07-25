package com.movie.test.page

import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.movie.test.R

class MovieDetailScreen : Screen<MovieDetailScreen>() {
    val synopsis: KTextView = KTextView { withId(R.id.movie_synopsis) }
    val bookButton: KTextView = KTextView { withId(R.id.book_button) }
}