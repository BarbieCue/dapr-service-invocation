package com.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

const val servicePort = 8083

fun main() {
    embeddedServer(Netty, port = servicePort, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {
    routing {

        get("/news") {
            call.respondText(listOf(
                "Bob married Alice",
                "Bob divorced Alice", 
                "A new species of bird has been discovered in thailand",
                "Hanover's city center is cleaner than ever").random())
        }
    }
}