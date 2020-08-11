package com.danger.kotlin.plugin.test.jacoco.model

data class Coverage(
    val projectCoverage: ProjectCoverage,
    val classCoverages: List<ClassCoverage>
)
