package com.danger.kotlin.plugin.test

import com.danger.kotlin.plugin.test.internal.InternalReport
import com.danger.kotlin.plugin.test.internal.InternalReportPlugin
import com.danger.kotlin.plugin.test.jacoco.JacocoReportPlugin
import com.danger.kotlin.plugin.test.model.Git
import com.danger.kotlin.plugin.test.model.GitHub
import systems.danger.kotlin.sdk.DangerPlugin

object TestPlugin : DangerPlugin() {

    private val internalReportPlugins: List<InternalReportPlugin>
        get() = listOf(
            JacocoReportPlugin
        )

    override val id: String
        get() = this.javaClass.name

    var gitInfo: Git? = null
    var gitHubInfo: GitHub? = null

    inline fun initGit(block: () -> Git) {
        gitInfo = block()
        println("INIT GIT $gitInfo")
    }

    inline fun initGitHub(block: () -> GitHub) {
        gitHubInfo = block()
        println("INIT GITHUB $gitHubInfo")
    }

    internal inline fun onGit(onGit: Git.() -> Unit) = gitInfo?.run {
        onGit(this)
    }

    internal inline fun onGitHub(onGitHub: GitHub.() -> Unit) = gitHubInfo?.run {
        onGitHub(this) }

    fun execute() {
        gitHubInfo?.let {
            if ((it.pullRequest.additions ?: 0) - (it.pullRequest.deletions ?: 0) > 300) {
                context.warn("Big PR, try to keep changes smaller if you can")
            }

            // Work in progress check
            if (it.pullRequest.title.contains("WIP", false)) {
                context.warn("PR is classed as Work in Progress")
            }

            context.message("Great work @${it.pullRequest.user.login} 🎉 , You might find a few suggestions from me for this Pull Request below 🙂")
        }

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
