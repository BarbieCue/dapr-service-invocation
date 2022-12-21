package com.example

import io.ktor.client.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.*

fun main() {
    embeddedServer(Netty, port = 8083, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    this@module.routing {
        get("/news") {

            val client = HttpClient(CIO) {
                install(ContentNegotiation) {
                    json(Json {
                        prettyPrint = true
                        ignoreUnknownKeys = true
                    })
                }
            }

            val daprPort = 3503
            val daprHost = "http://localhost"
            val daprTargetAppId = "news-service"
            val daprTargetMethod = "/info"

            val response = client.get("${daprHost}:${daprPort}${daprTargetMethod}") {
                contentType(ContentType.Application.Json)
                header("dapr-app-id", daprTargetAppId)
                setBody("Hi, im foo service. I call the dapr sidecar of bar service.")
            }

            call.respondText(response.bodyAsText())
        }
    }
}