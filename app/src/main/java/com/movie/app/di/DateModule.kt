package com.movie.app.di

import com.movie.app.core.DateTimeHelper
import com.movie.app.core.IDateTimeHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DateModule {

    @Provides
    @Singleton
    fun providesDateTimeHelper(): IDateTimeHelper {
        return DateTimeHelper()
    }

}