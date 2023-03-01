package com.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

const val servicePort = 8082

fun main() {
    embeddedServer(Netty, port = servicePort, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {
    routing {

        get("/time") {
            call.respondText("${listOf("5", "6", "9", "11", "12").random()} o'clock")
        }
    }
}