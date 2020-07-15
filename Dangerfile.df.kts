@file:DependsOn("./danger-kotlin-test/build/libs/danger-kotlin-test-0.0.1.jar")

import com.danger.kotlin.plugin.test.TestPlugin
import systems.danger.kotlin.*

register plugin TestPlugin

danger(args) {

    val allSourceFiles = git.modifiedFiles + git.createdFiles
    val sourceChanges = allSourceFiles.firstOrNull { it.contains("src") }

    onGitHub {
        val isTrivial = pullRequest.title.contains("#trivial")

        // Big PR Check
        if ((pullRequest.additions ?: 0) - (pullRequest.deletions ?: 0) > 300) {
            warn("Big PR, try to keep changes smaller if you can")
        }

        // Work in progress check
        if (pullRequest.title.contains("WIP", false)) {
            warn("PR is classed as Work in Progress")
        }

        message("Great work @${pullRequest.user.login} 🎉 , You might find a few suggestions from me for this Pull Request below 🙂")

        TestPlugin().helloPlugin()
    }
}

