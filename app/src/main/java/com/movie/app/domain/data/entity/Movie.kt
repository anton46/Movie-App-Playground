package com.movie.app.domain.data.entity

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id") val movieId: Int,
    @SerializedName("poster_path") val posterUrl: String?,
    @SerializedName("title") val title: String,
    @SerializedName("popularity") val popularity: Float
)