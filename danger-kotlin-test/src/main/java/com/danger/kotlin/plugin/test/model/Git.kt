package com.danger.kotlin.plugin.test.model

typealias FilePath = String

/**
 * The git specific metadata for a pull request
 *
 * @property modifiedFiles Modified file paths relative to the git root.
 * @property createdFiles Newly created file paths relative to the git root.
 * @property deletedFiles Removed file paths relative to the git root.
 */
data class Git(
    val modifiedFiles: List<FilePath>,
    val createdFiles: List<FilePath>,
    val deletedFiles: List<FilePath>,
    val commits: List<GitCommit>
) {

    /**
     * A platform agnostic reference to a git commit.
     *
     * @property sha The SHA for the commit.
     * @property author Who wrote the commit.
     * @property committer Who shipped the code.
     * @property message The message for the commit.
     * @property parents SHAs for the commit's parents.
     * @property url The URL for the commit.
     */
    data class GitCommit(
        val sha: String?,
        val author: GitCommitAuthor,
        val committer: GitCommitAuthor,
        val message: String,
        val parents: List<String>?,
        val url: String
    )

    /**
     * A platform agnostic reference to a git commit.
     *
     * @property name The display name for the author.
     * @property email The email for the author.
     * @property date The ISO8601 date string for the commit.
     */
    data class GitCommitAuthor(
        val name: String,
        val email: String,
        val date: String
    )
}
