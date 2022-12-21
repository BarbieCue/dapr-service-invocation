package com.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

const val port = 8081

fun main() {
    embeddedServer(Netty, port = port, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {
    this@module.routing {
        get("/weather") {
            call.respondText("${listOf("rainy at 15", "sunny at 32", "foggy at 5").random()} degrees")
        }
    }
}