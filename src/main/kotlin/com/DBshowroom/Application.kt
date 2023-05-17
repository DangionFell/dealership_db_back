package com.DBshowroom

import com.DBshowroom.features.routing.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import com.DBshowroom.plugins.*
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database



fun main() {
    val config = HikariConfig("hikari.properties")
    val dataSource = HikariDataSource(config)
    Database.connect(dataSource)
//    Database.connect(url = "jdbc:postgresql://localhost:5432/dealership",
//        driver = "org.postgresql.Driver",
//        user = "postgres",
//        password = "123456")
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureRouting()


    configureShowroomRouting()
    configureClientRouting()
    configureManagerRouting()
    configureCarRouting()
    configureContractRouting()
}
