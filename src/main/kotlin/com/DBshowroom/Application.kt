package com.DBshowroom

import com.DBshowroom.features.login.configureLoginRouting
import com.DBshowroom.features.register.configureRegisterRouting
import com.DBshowroom.features.routing.configureClientRouting
import com.DBshowroom.features.routing.configureShowroomRouting
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.cio.*
import com.DBshowroom.plugins.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database

fun main() {
    Database.connect(url = "jdbc:postgresql://localhost:5432/dealership",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "123456")
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureRouting()
    configureLoginRouting()
    configureRegisterRouting()

    configureShowroomRouting()
    configureClientRouting()
}
