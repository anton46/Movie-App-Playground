package com.movie.app.page

import android.content.ClipData
import android.view.View
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.movie.app.R
import org.hamcrest.Matcher

class HomeScreen : Screen<HomeScreen>() {
    val movies : KRecyclerView = KRecyclerView({
        withId(R.id.movie_list)
    }, itemTypeBuilder = {
        itemType(::MovieItem)
    })

    class MovieItem(parent: Matcher<View>) : KRecyclerItem<ClipData.Item>(parent) {
        val title: KTextView = KTextView(parent) { withId(R.id.movie_title) }
        val popularity: KTextView = KTextView(parent) { withId(R.id.movie_popularity) }
    }
}