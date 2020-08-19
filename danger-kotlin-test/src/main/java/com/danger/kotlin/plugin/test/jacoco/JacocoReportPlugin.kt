package com.danger.kotlin.plugin.test.jacoco

import com.danger.kotlin.plugin.test.internal.InternalReport
import com.danger.kotlin.plugin.test.internal.InternalReportPlugin
import com.danger.kotlin.plugin.test.jacoco.model.JacocoConfiguration

object JacocoReportPlugin : InternalReportPlugin {

    override fun report(): InternalReport {
        return when (val coverage = JacocoParser.parse("./app/build/reports/jacoco/jacocoDebugUnitTestReport/jacocoDebugUnitTestReport.xml")) {
            null -> InternalReport.Fail("No coverage report found")
            else -> {
                val configuration = getCoverageConfiguration()
                var reportMarkdown = "### Jacoco code coverage ${coverage.projectCoverage.coverage} % ${getCoverageIconStatus(coverage.projectCoverage.coverage, configuration.minimumProjectCoverage)}\n"
                coverage.classCoverages.forEach {
                    reportMarkdown += "|:---|:---:|:---:|:---:|\n"
                    reportMarkdown += "| ${it.path} | ${it.coverage} |${it.coverage} | ${getCoverageIconStatus(it.coverage, configuration.minimumClassCoverage)} |\n"
                }
                InternalReport.Markdown(reportMarkdown)
            }
        }
    }

    private fun getCoverageIconStatus(coverage: Double, minimumCoverage: Double) = when {
        coverage > minimumCoverage -> ":white_check_mark:"
        else -> ":warning:"
    }

    private fun getCoverageConfiguration() = JacocoConfiguration(
        minimumClassCoverage = 0.0,
        minimumProjectCoverage = 0.0
    )
}
