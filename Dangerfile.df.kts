@file:DependsOn("./danger-kotlin-test/build/libs/danger-kotlin-test-0.0.1.jar")

import com.danger.kotlin.plugin.test.TestPlugin
import com.danger.kotlin.plugin.test.model.DataMapper
import com.danger.kotlin.plugin.test.model.Mapper
import com.danger.kotlin.plugin.test.model.DataMapper.Companion.arrayToListMapper
import com.danger.kotlin.plugin.test.model.DataMapper.Companion.listMapper
import systems.danger.kotlin.*

typealias GitHubClient = com.danger.kotlin.plugin.test.model.GitHub
typealias GitHubUserTypeClient = com.danger.kotlin.plugin.test.model.GitHubUserType
typealias GitHubIssueClientState = com.danger.kotlin.plugin.test.model.GitHubIssueState
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
typealias GitHubPullRequestStateClient = com.danger.kotlin.plugin.test.model.GitHubPullRequestState
typealias GitHubReviewStateClient = com.danger.kotlin.plugin.test.model.GitHubReviewState
typealias GitHubMilestoneStateClient = com.danger.kotlin.plugin.test.model.GitHubMilestoneState
typealias GitClient = com.danger.kotlin.plugin.test.model.Git
typealias GitCommitClient = com.danger.kotlin.plugin.test.model.Git.GitCommit
typealias GitCommitAuthorClient = com.danger.kotlin.plugin.test.model.Git.GitCommitAuthor

register plugin TestPlugin

danger(args) {
    val gitCommitAuthorMapper = DataMapper<GitCommitAuthor, GitCommitAuthorClient>()
    val gitCommitMapper = DataMapper<GitCommit, GitCommitClient>()
        .register("author", gitCommitAuthorMapper)
        .register("committer", gitCommitAuthorMapper)
        .register("parents", arrayToListMapper<String>())

    onGit {
        TestPlugin.initGit {
            val gitMapper = DataMapper<Git, GitClient>()
                .register("commits", listMapper(gitCommitMapper))
                .register("modifiedFiles", arrayToListMapper<String>())
                .register("createdFiles", arrayToListMapper<String>())
                .register("deletedFiles", arrayToListMapper<String>())
            gitMapper(this)
        }
    }

    onGitHub {
        TestPlugin.initGitHub {
            val gitHubUserTypeMapper = object : Mapper<GitHubUserType, GitHubUserTypeClient> {
                override fun invoke(data: GitHubUserType) = when (data) {
                    GitHubUserType.USER -> GitHubUserTypeClient.USER
                    GitHubUserType.BOT -> GitHubUserTypeClient.BOT
                    GitHubUserType.ORGANIZATION -> GitHubUserTypeClient.ORGANIZATION
                }
            }

            val gitHubIssueStateMapper = object : Mapper<GitHubIssueState, GitHubIssueClientState> {
                override fun invoke(state: GitHubIssueState) = when (state) {
                    GitHubIssueState.OPEN -> GitHubIssueClientState.OPEN
                    GitHubIssueState.CLOSED -> GitHubIssueClientState.CLOSED
                    GitHubIssueState.LOCKED -> GitHubIssueClientState.LOCKED
                }
            }

            val gitHubPullRequestStateMapper = object : Mapper<GitHubPullRequestState, GitHubPullRequestStateClient> {
                override fun invoke(state: GitHubPullRequestState) = when (state) {
                    GitHubPullRequestState.OPEN -> GitHubPullRequestStateClient.OPEN
                    GitHubPullRequestState.CLOSED -> GitHubPullRequestStateClient.CLOSED
                    GitHubPullRequestState.LOCKED -> GitHubPullRequestStateClient.LOCKED
                    GitHubPullRequestState.MERGED -> GitHubPullRequestStateClient.MERGED
                }
            }

            val gitHubMilestoneStateMapper = object : Mapper<GitHubMilestoneState, GitHubMilestoneStateClient> {
                override fun invoke(state: GitHubMilestoneState) = when (state) {
                    GitHubMilestoneState.OPEN -> GitHubMilestoneStateClient.OPEN
                    GitHubMilestoneState.CLOSE -> GitHubMilestoneStateClient.CLOSE
                    GitHubMilestoneState.ALL -> GitHubMilestoneStateClient.ALL
                }
            }

            val gitHubReviewStateMapper = object : Mapper<GitHubReviewState, GitHubReviewStateClient> {
                override fun invoke(state: GitHubReviewState) = when (state) {
                    GitHubReviewState.APPROVED -> GitHubReviewStateClient.APPROVED
                    GitHubReviewState.CHANGES_REQUESTED -> GitHubReviewStateClient.CHANGES_REQUESTED
                    GitHubReviewState.COMMENTED -> GitHubReviewStateClient.COMMENTED
                    GitHubReviewState.DISMISSED -> GitHubReviewStateClient.DISMISSED
                    GitHubReviewState.PENDING -> GitHubReviewStateClient.PENDING
                }
            }

            val githubUserMapper = DataMapper<GitHubUser, GitHubUserClient>()
                .register("type", gitHubUserTypeMapper)

            val githubTeamMapper = DataMapper<GitHubTeam, GithubTeamClient>()

            val gitHubMilestoneMapper = DataMapper<GitHubMilestone, GitHubMilestoneClient>()
                .register("state", gitHubMilestoneStateMapper)
                .register("creator", githubUserMapper)

            val githubIssueClient = DataMapper<GitHubIssueLabel, GitHubIssueLabelClient>()

            val githubIssueMapper = DataMapper<GitHubIssue, GitHubIssueClient>()
                .register("user", githubUserMapper)
                .register("assignee", githubUserMapper)
                .register("assignees", arrayToListMapper(githubUserMapper))
                .register("milestone", gitHubMilestoneMapper)
                .register("labels", arrayToListMapper(githubIssueClient))
                .register("state", gitHubIssueStateMapper)

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
                .register("state", gitHubPullRequestStateMapper)

            val githubReviewMapper = DataMapper<GitHubReview, GitHubReviewClient>()
                .register("user", githubUserMapper)
                .register("state", gitHubReviewStateMapper)

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

