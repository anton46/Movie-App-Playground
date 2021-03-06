package com.movie.app.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.movie.app.di.HasActivityComponent

abstract class BaseActivity<V : BaseView<*>, P : BasePresenter<V, *>> : AppCompatActivity(), HasActivityComponent {

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        getPresenter().attachView(getView())
        super.onCreate(savedInstanceState)
    }

    abstract fun inject()

    abstract fun getPresenter(): P

    abstract fun getView(): V
}