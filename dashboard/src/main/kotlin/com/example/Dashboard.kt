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

const val port = 8080
const val daprSidecarPort = 3500

fun main() {
    embeddedServer(Netty, port = port, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {
    this@module.routing {
        get("/dashboard") {

            val client = HttpClient(CIO) {
                install(ContentNegotiation) {
                    json(Json {
                        prettyPrint = true
                        ignoreUnknownKeys = true
                    })
                }
            }

            val weather = client.get("http://localhost:${daprSidecarPort}/weather") {
                contentType(ContentType.Application.Json)
                header("dapr-app-id", "weather-service")
            }.bodyAsText()

            val time = client.get("http://localhost:${daprSidecarPort}/time") {
                contentType(ContentType.Application.Json)
                header("dapr-app-id", "time-service")
            }.bodyAsText()

            val news = client.get("http://localhost:${daprSidecarPort}/news") {
                contentType(ContentType.Application.Json)
                header("dapr-app-id", "news-service")
            }.bodyAsText()

            call.respondText("Hi, it is ${time}. The weather is $weather and here are some interesting news for you: $news")
        }
    }
}