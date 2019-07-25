package com.movie.test.di

import com.movie.test.ui.di.ActivityComponent

interface HasActivityComponent : HasApplicationComponent {
    fun getActivityComponent(): ActivityComponent
}