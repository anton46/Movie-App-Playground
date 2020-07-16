package com.danger.kotlin.plugin.test.internal

sealed class InternalReport {
    data class Warn(val message: String, val file: String? = null, val line: Int? = null) :
        InternalReport()

    data class Fail(val message: String, val file: String? = null, val line: Int? = null) :
        InternalReport()

    data class Markdown(val message: String, val file: String? = null, val line: Int? = null) :
        InternalReport()

    data class Message(val message: String, val file: String? = null, val line: Int? = null) :
        InternalReport()
}
