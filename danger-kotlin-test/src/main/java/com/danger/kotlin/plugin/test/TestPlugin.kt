package com.danger.kotlin.plugin.test

import systems.danger.kotlin.sdk.DangerPlugin
import java.io.File

object TestPlugin : DangerPlugin() {

    override val id: String
        get() = this.javaClass.name

    fun execute() {
        context.message("Hello Danger Plugin!")
        runCoverageReport()

    }

    private fun runCoverageReport() {
        val coverageRegex = "Total.*?([0-9]{0,3})%".toRegex()
        val coverageReport =
            File("./app/build/reports/jacoco/jacocoDebugUnitTestReport/html/index.html").readText()
        val coveragePercentageMatch = coverageRegex.find(coverageReport)
        coveragePercentageMatch?.let {
            val coveragePercentage = it.value.split(">").last()
            context.markdown("### Jacoco code coverage $coveragePercentage :white_check_mark:")
        } ?: kotlin.run {
            context.warn("Forget to run coverage report tasks?")
        }
    }
}
