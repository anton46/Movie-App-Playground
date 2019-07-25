package com.movie.test.net.retrofit

import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.IOException
import java.lang.reflect.Type

internal class GsonResponseBodyConverter<T>(private val gson: Gson,
                                            private val type: Type)
    : Converter<ResponseBody, T> {

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T? {
        val reader = value.charStream()
        try {
            return gson.fromJson<T>(reader, type)
        } finally {
            reader.let {
                try {
                    it.close()
                } catch (ignored: IOException) {
                }

            }
        }
    }
}
