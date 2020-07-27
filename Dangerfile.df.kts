@file:DependsOn("./danger-kotlin-test/build/libs/danger-kotlin-test-0.0.1.jar")

import com.danger.kotlin.plugin.test.TestPlugin
import com.danger.kotlin.plugin.test.model.DataMapper
import com.danger.kotlin.plugin.test.model.DataMapper.Companion.arrayToListMapper
import systems.danger.kotlin.*

typealias GitHubClient = com.danger.kotlin.plugin.test.model.GitHub
typealias GitHubIssueClient = com.danger.kotlin.plugin.test.model.GitHubIssue
typealias GitHubUserClient = com.danger.kotlin.plugin.test.model.GitHubUser
typealias GithubTeamClient = com.danger.kotlin.plugin.test.model.GitHubTeam
typealias GitHubPRClient = com.danger.kotlin.plugin.test.model.GitHubPR
typealias GitHubMilestoneClient = com.danger.kotlin.plugin.test.model.GitHubMilestone
typealias GitHubIssueLabelClient = com.danger.kotlin.plugin.test.model.GitHubIssueLabel
typealias GitHubMergeRefClient = com.danger.kotlin.plugin.test.model.GitHubMergeRef
typealias GitHubRepoClient = com.danger.kotlin.plugin.test.model.GitHubRepo
typealias GitHubCommitClient = com.danger.kotlin.plugin.test.model.GitHubCommit
typealias GitHubReviewClient = com.danger.kotlin.plugin.test.model.GitHubReview
typealias GitHubRequestedReviewersClient = com.danger.kotlin.plugin.test.model.GitHubRequestedReviewers
typealias GitClient = com.danger.kotlin.plugin.test.model.Git
typealias GitCommitClient = com.danger.kotlin.plugin.test.model.Git.GitCommit
typealias GitCommitAuthorClient = com.danger.kotlin.plugin.test.model.Git.GitCommitAuthor

register plugin TestPlugin

danger(args) {

   /* val allSourceFiles = git.modifiedFiles + git.createdFiles
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

        TestPlugin.execute()
    }*/


    val gitCommitAuthorMapper = DataMapper<GitCommitAuthor, GitCommitAuthorClient>()
    val gitCommitMapper = DataMapper<GitCommit, GitCommitClient>()
        .register("author", gitCommitAuthorMapper)
        .register("committer", gitCommitAuthorMapper)

    onGit {
        TestPlugin.initGit {
            val gitMapper = DataMapper<Git, GitClient>()
                .register("commits", arrayToListMapper(gitCommitMapper))
                .register("modifiedFiles", arrayToListMapper<String>())
                .register("createdFiles", arrayToListMapper<String>())
                .register("deletedFiles", arrayToListMapper<String>())
            gitMapper(this)
        }
    }

    onGitHub {
        TestPlugin.initGitHub {
            val githubUserMapper = DataMapper<GitHubUser, GitHubUserClient>()
            val githubTeamMapper = DataMapper<GitHubTeam, GithubTeamClient>()
            val gitHubMilestoneMapper = DataMapper<GitHubMilestone, GitHubMilestoneClient>()
            val githubIssueClient = DataMapper<GitHubIssueLabel, GitHubIssueLabelClient>()

            val githubIssueMapper = DataMapper<GitHubIssue, GitHubIssueClient>()
                .register("user", githubUserMapper)
                .register("assignee", githubUserMapper)
                .register("assignees", arrayToListMapper(githubUserMapper))
                .register("milestone", gitHubMilestoneMapper)
                .register("labels", githubIssueClient)

            val githubRepoMapper = DataMapper<GitHubRepo, GitHubRepoClient>()
            val githubMergeRefMapper = DataMapper<GitHubMergeRef, GitHubMergeRefClient>()
                .register("user", githubUserMapper)
                .register("repo", githubRepoMapper)
            val githubCommitMapper = DataMapper<GitHubCommit, GitHubCommitClient>()
                .register("author", githubUserMapper)
                .register("committer", githubUserMapper)
                .register("commit", gitCommitMapper)
            val githubPRMapper = DataMapper<GitHubPR, GitHubPRClient>()
                .register("user", githubUserMapper)
                .register("assignee", githubUserMapper)
                .register("assignees", arrayToListMapper(githubUserMapper))
                .register("head", githubMergeRefMapper)
                .register("base", githubMergeRefMapper)
                .register("milestone", gitHubMilestoneMapper)
            val githubReviewMapper = DataMapper<GitHubReview, GitHubReviewClient>()
                .register("user", githubUserMapper)
            val gitHubRequestedReviewersMapper = DataMapper<GitHubRequestedReviewers, GitHubRequestedReviewersClient>()
                .register("users", arrayToListMapper(githubUserMapper))
                .register("teams", arrayToListMapper(githubTeamMapper))
            val gitHubMapper = DataMapper<GitHub, GitHubClient>()
                .register("issue", githubIssueMapper)
                .register("pullRequest", githubPRMapper)
                .register("commits", arrayToListMapper(githubCommitMapper))
                .register("reviews", arrayToListMapper(githubReviewMapper))
                .register("requestedReviewers", gitHubRequestedReviewersMapper)
            gitHubMapper(this)
        }
        TestPlugin.execute()
    }
}

