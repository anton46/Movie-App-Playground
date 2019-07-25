package com.movie.app.di

import com.movie.app.ui.di.ActivityComponent

interface HasActivityComponent : HasApplicationComponent {
    fun getActivityComponent(): ActivityComponent
}