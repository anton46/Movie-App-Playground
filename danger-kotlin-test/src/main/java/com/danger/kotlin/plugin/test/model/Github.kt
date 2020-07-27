package com.danger.kotlin.plugin.test.model

import java.util.Date

/**
 * The GitHub metadata for your pull request.
 */
data class GitHub(
    val issue: GitHubIssue,
    val pullRequest: GitHubPR,
    val commits: List<GitHubCommit>,
    val reviews: List<GitHubReview>,
    val requestedReviewers: GitHubRequestedReviewers
)

enum class GitHubPullRequestState(val value: String) {
    CLOSED("closed"),
    OPEN("open"),
    MERGED("merged"),
    LOCKED("locked")
}

/**
 * The GitHub pull request
 *
 * @property number The number of the pull request.
 * @property title The title of the pull request.
 * @property body The markdown body message of the pull request.
 * @property user The user who submitted the pull request.
 * @property assignee The user who submitted the pull request.
 * @property assignees The users who are assigned to the pull request.
 * @property createdAt The date for when the pull request was created.
 * @property updatedAt The date for when the pull request was updated.
 * @property closedAt The date for when the pull request was closed.
 * @property mergedAt The date for when the pull request was merged.
 * @property head The merge reference for the _other_ repo.
 * @property base The merge reference for the _this_ repo.
 * @property state The state for the pull request: open, closed, locked, merged.
 * @property isLocked A boolean indicating if the pull request has been locked to contributors only.
 * @property isMerged A boolean indicating if the pull request has been merged.
 * @property commitCount The number of commits in the pull request.
 * @property commentCount The number of comments in the pull request.
 * @property reviewCommentCount The number of review-specific comments in the pull request.
 * @property additions The number of added lines in the pull request.
 * @property deletions The number of deleted lines in the pull request.
 * @property changedFiles The number of files changed in the pull request.
 * @property milestone The milestone of the pull request
 * @property htmlURL The link back to this PR as user-facing
 */
data class GitHubPR(
    val number: Int,
    val title: String,
    val body: String,
    val user: GitHubUser,
    val assignee: GitHubUser?,
    val assignees: List<GitHubUser>,
    val createdAt: Date,
    val updatedAt: Date,
    val closedAt: Date?,
    val mergedAt: Date?,
    val head: GitHubMergeRef,
    val base: GitHubMergeRef,
    val state: GitHubPullRequestState,
    val isLocked: Boolean,
    val isMerged: Boolean?,
    val commitCount: Int?,
    val commentCount: Int?,
    val reviewCommentCount: Int?,
    val additions: Int?,
    val deletions: Int?,
    val changedFiles: Int?,
    val milestone: GitHubMilestone?,
    val htmlURL: String
)

/**
 * A GitHub team
 *
 * @property id The UUID for the team.
 * @property name The team name.
 */
data class GitHubTeam(
    val id: Long,
    val name: String
)

/**
 * Represents the payload for a PR's requested reviewers value.
 *
 * @property users The list of users of whom a review has been requested..
 * @property teams The list of teams of whom a review has been requested.
 */
data class GitHubRequestedReviewers(
    val users: List<GitHubUser>,
    val teams: List<GitHubTeam>
)

/**
 * Represents a branch in PR
 *
 * @property label The human display name for the merge reference, e.g. "artsy:master".
 * @property ref The reference point for the merge, e.g. "master".
 * @property sha The reference point for the merge, e.g. "704dc55988c6996f69b6873c2424be7d1de67bbe".
 * @property user The user that owns the merge reference e.g. "artsy".
 * @property repo The repo from which the reference comes from.
 */
data class GitHubMergeRef(
    val label: String,
    val ref: String,
    val sha: String,
    val user: GitHubUser,
    val repo: GitHubRepo
)

/**
 *  Represents the payload for a repo.
 *
 * @property id Generic UUID.
 * @property name The name of the repo, e.g. "danger-swift".
 * @property fullName The full name of the owner + repo, e.g. "Danger/danger-swift"
 * @property isPrivate A boolean stating whether the repo is publicly accessible.
 * @property description A textual description of the repo.
 * @property isFork A boolean stating whether the repo is a fork.
 * @property htmlURL The root web URL for the repo, e.g. https://github.com/artsy/emission
 */
data class GitHubRepo(
    val id: Long,
    val name: String,
    val fullName: String,
    val isPrivate: Boolean,
    val description: String?,
    val isFork: Boolean,
    val htmlURL: String
)

enum class GitHubReviewState(val value: String) {
    APPROVED("APPROVED"),
    CHANGES_REQUESTED("CHANGES_REQUESTED"),
    COMMENTED("COMMENTED"),
    PENDING("PENDING"),
    DISMISSED("DISMISSED")
}

/**
 * Represents the payload for a PR review.
 *
 * @property user The user who has completed the review or has been requested to review.
 * @property id The id for the review (if a review was made).
 * @property body The body of the review (if a review was made).
 * @property commitId The commit ID the review was made on (if a review was made).
 * @property state The state of the review (if a review was made).
 */
data class GitHubReview(
    val user: GitHubUser,
    val id: Long?,
    val body: String?,
    val commitId: String?,
    val state: GitHubReviewState?
)

/**
 * A GitHub specific implementation of a git commit.
 *
 * @property sha The SHA for the commit.
 * @property url The URL for the commit on GitHub.
 * @property author The GitHub user who wrote the code.
 * @property commit The raw commit metadata.
 * @property committer The GitHub user who shipped the code.
 */
data class GitHubCommit(
    val sha: String,
    val url: String,
    val author: GitHubUser?,
    val commit: Git.GitCommit,
    val committer: GitHubUser?
)

enum class GitHubIssueState(val value: String) {
    CLOSED("closed"),
    OPEN("open"),
    LOCKED("locked")
}

/**
 * The GitHub pull request
 *
 * @property number The number of the pull request.
 * @property title The title of the pull request.
 * @property body The markdown body message of the pull request.
 * @property user The user who submitted the pull request.
 * @property assignee The user who submitted the pull request.
 * @property assignees The users who are assigned to the pull request.
 * @property createdAt The date for when the pull request was created.
 * @property updatedAt The date for when the pull request was updated.
 * @property closedAt The date for when the pull request was closed.
 * @property mergedAt The date for when the pull request was merged.
 * @property head The merge reference for the _other_ repo.
 * @property base The merge reference for the _this_ repo.
 * @property state The state for the pull request: open, closed, locked, merged.
 * @property isLocked A boolean indicating if the pull request has been locked to contributors only.
 * @property isMerged A boolean indicating if the pull request has been merged.
 * @property commitCount The number of commits in the pull request.
 * @property commentCount The number of comments in the pull request.
 * @property reviewCommentCount The number of review-specific comments in the pull request.
 * @property additions The number of added lines in the pull request.
 * @property deletions The number of deleted lines in the pull request.
 * @property changedFiles The number of files changed in the pull request.
 * @property milestone The milestone of the pull request
 */
data class GitHubIssue(
    val id: Long,
    val number: Int,
    val title: String,
    val user: GitHubUser,
    val state: GitHubIssueState,
    val isLocked: Boolean,
    val body: String,
    val commentCount: Int,
    val assignee: GitHubUser?,
    val assignees: List<GitHubUser>,
    val milestone: GitHubMilestone?,
    val createdAt: Date,
    val updatedAt: Date,
    val closedAt: Date?,
    val labels: List<GitHubIssueLabel>
)

/**
 * @property id The id number of this label.
 * @property url The URL that links to this label.
 * @property name The name of the label.
 * @property color TThe color associated with this label.
 */
data class GitHubIssueLabel(
    val id: Long,
    val url: String,
    val name: String,
    val color: String
)

enum class GitHubUserType {
    USER,
    ORGANIZATION,
    BOT
}

/**
 * A GitHub user account.
 *
 * @property id The UUID for the user organization.
 * @property login The handle for the user or organization.
 * @property type The type of user: user or organization.
 */
data class GitHubUser(
    val id: Long,
    val login: String,
    val type: GitHubUserType,
    val avatarUrl: String
)

enum class GitHubMilestoneState(val value: String) {
    CLOSE("close"),
    OPEN("open"),
    ALL("all")
}

/**
 * A GitHub milestone
 *
 * @property id The id number of this milestone
 * @property number The number of this milestone
 * @property state The state of this milestone: open, closed, all.
 * @property title The title of this milestone.
 * @property description The description of this milestone.
 * @property creator The user who created this milestone.
 * @property openIssues The number of open issues in this milestone.
 * @property closedIssues The number of closed issues in this milestone.
 * @property createdAt The date  for when this milestone was created.
 * @property updatedAt The date for when the milestone was update.
 * @property closedAt The date for when the milestone was closed.
 * @property dueOn The date for the due of this milestone.
 */
data class GitHubMilestone(
    val id: Long,
    val number: Int,
    val state: GitHubMilestoneState,
    val title: String,
    val description: String,
    val creator: GitHubUser,
    val openIssues: Int,
    val closedIssues: Int,
    val createdAt: Date,
    val updatedAt: Date,
    val closedAt: Date,
    val dueOn: Date
)
