package com.danger.kotlin.plugin.test

import com.danger.kotlin.plugin.test.internal.InternalReport
import com.danger.kotlin.plugin.test.internal.InternalReportPlugin
import com.danger.kotlin.plugin.test.jacoco.JacocoReportPlugin
import systems.danger.kotlin.sdk.DangerPlugin

object TestPlugin : DangerPlugin() {

    private val internalReportPlugins: List<InternalReportPlugin>
        get() = listOf(
            JacocoReportPlugin
        )

    override val id: String
        get() = this.javaClass.name

    fun execute() {
        internalReportPlugins.forEach {
            when (val report = it.report()) {
                is InternalReport.Warn -> {
                    if (report.file != null && report.line != null)
                        context.warn(report.message, report.file, report.line)
                    else
                        context.warn(report.message)
                }
                is InternalReport.Fail -> {
                    if (report.file != null && report.line != null)
                        context.fail(report.message, report.file, report.line)
                    else
                        context.fail(report.message)
                }
                is InternalReport.Markdown -> {
                    if (report.file != null && report.line != null)
                        context.markdown(report.message, report.file, report.line)
                    else
                        context.markdown(report.message)
                }
                is InternalReport.Message -> {
                    if (report.file != null && report.line != null)
                        context.message(report.message, report.file, report.line)
                    else
                        context.message(report.message)
                }
            }
        }
    }
}
