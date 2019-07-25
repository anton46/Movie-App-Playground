package com.movie.app.net.retrofit
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class GsonConverterFactory private constructor(private val gson: Gson) : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>?,
        retrofit: Retrofit?
    ): Converter<ResponseBody, Any> {
        return GsonResponseBodyConverter(gson, type)
    }

    companion object {
        fun create(gson: Gson): GsonConverterFactory {
            return GsonConverterFactory(gson)
        }
    }
}
