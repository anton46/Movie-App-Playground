package com.movie.app.net.data.entity

import com.google.gson.annotations.SerializedName

data class Language(
    @SerializedName("iso_3166_1") val iso: String,
    @SerializedName("name") val languageName: String
)