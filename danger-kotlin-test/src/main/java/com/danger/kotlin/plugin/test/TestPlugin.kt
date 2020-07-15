package com.danger.kotlin.plugin.test

import systems.danger.kotlin.sdk.DangerPlugin

class TestPlugin : DangerPlugin() {

    override val id: String
        get() = this.javaClass.name

    fun helloPlugin() {
        context.message("Hello Danger Plugin!")
    }
}
