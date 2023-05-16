package com.DBshowroom.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class Text(
    val text: String
)




fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
//    routing {
//        get("/") {
//                call.respond(Text("lalala"))
//            }
//    }
}
