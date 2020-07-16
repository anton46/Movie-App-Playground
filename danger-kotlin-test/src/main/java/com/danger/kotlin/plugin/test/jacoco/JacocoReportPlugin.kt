package com.danger.kotlin.plugin.test.jacoco

import com.danger.kotlin.plugin.test.internal.InternalReport
import com.danger.kotlin.plugin.test.internal.InternalReportPlugin
import com.danger.kotlin.plugin.test.jacoco.model.JacocoConfiguration

object JacocoReportPlugin : InternalReportPlugin {

    override fun report(): InternalReport {
        val configuration = getJacocoConfiguration()
        val coverage =
            JacocoParser.parse("./app/build/reports/jacoco/jacocoDebugUnitTestReport/jacocoDebugUnitTestReport.xml")
        return when (coverage) {
            null -> InternalReport.Fail("No coverage report found")
            else -> InternalReport.Markdown("### Jacoco code coverage ${coverage.projectCoverage.coverage} % ${getCoverageIconStatus(coverage.projectCoverage.coverage, configuration.minimumProjectCoverage)}")
        }
    }

    private fun getCoverageIconStatus(coverage: Double, minimumCoverage: Double) = when {
        coverage > minimumCoverage -> ":white_check_mark:"
        else -> ":warning:"
    }

    private fun getJacocoConfiguration() = JacocoConfiguration(
        minimumClassCoverage = 90.0,
        minimumProjectCoverage = 75.0
    )
}

fun main() {
    println(JacocoReportPlugin.report())
}
