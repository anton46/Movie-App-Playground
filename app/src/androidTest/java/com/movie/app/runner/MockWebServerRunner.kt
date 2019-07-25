package com.movie.app.runner

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.test.runner.AndroidJUnitRunner
import android.os.StrictMode
import androidx.test.platform.app.InstrumentationRegistry
import com.linkedin.android.testbutler.TestButler
import com.movie.app.EspressoApplication
import kotlin.reflect.jvm.jvmName

class MockWebServerRunner: AndroidJUnitRunner() {

    override fun onStart() {
        TestButler.setup(InstrumentationRegistry.getInstrumentation().targetContext)
        super.onStart()
    }

    override fun finish(resultCode: Int, results: Bundle?) {
        TestButler.teardown(InstrumentationRegistry.getInstrumentation().targetContext)
        super.finish(resultCode, results)
    }

    override fun onCreate(arguments: Bundle?) {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
        super.onCreate(arguments)
    }

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, EspressoApplication::class.jvmName, context)
    }
}