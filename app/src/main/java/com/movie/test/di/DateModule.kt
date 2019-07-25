package com.movie.test.di

import com.movie.test.core.DateTimeHelper
import com.movie.test.core.IDateTimeHelper
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