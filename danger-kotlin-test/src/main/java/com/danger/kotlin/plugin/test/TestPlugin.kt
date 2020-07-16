package com.danger.kotlin.plugin.test

import systems.danger.kotlin.sdk.DangerPlugin
import java.io.File

object TestPlugin : DangerPlugin() {

    override val id: String
        get() = this.javaClass.name

    fun execute() {
        context.message("Hello Danger Plugin!")
        context.markdown("### Jacoco code coverage ${getCoverageReport()}")
    }

    private fun getCoverageReport(): String {
        val coverageRegex = "Total.*?([0-9]{0,3})%".toRegex()
        val coverageReport =
            File("./app/build/reports/jacoco/jacocoDebugUnitTestReport/html/index.html").readText()
        val coveragePercentageMatch = coverageRegex.find(coverageReport)
        coveragePercentageMatch?.let {
            val coveragePercentage = it.value.split(">").last()
            println(coveragePercentage)
        }
        return ""
    }
}
