package com.DBshowroom.features.register

import com.DBshowroom.cache.InMemoryCache
import com.DBshowroom.cache.TokenCache
import com.DBshowroom.features.login.LoginReciveRemote
import com.DBshowroom.features.login.LoginResponseRemote
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.configureRegisterRouting() {
    routing {
        post("/register") {
            val receive = call.receive<RegisterReciveRemote>()

            if (InMemoryCache.userList.map { it.login }.contains(receive.login)){
                call.respond(HttpStatusCode.Conflict, "User already exists")
            }

            val token = UUID.randomUUID().toString()
            InMemoryCache.userList.add(receive)
            InMemoryCache.tokenList.add(TokenCache(login = receive.login, token = token))


            call.respond(RegisterResponseRemote(token = token))
        }
    }
}