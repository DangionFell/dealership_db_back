package com.DBshowroom.features.login

import com.DBshowroom.cache.InMemoryCache
import com.DBshowroom.cache.TokenCache
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.configureLoginRouting() {
    routing {
        post("/login") {
            val receive = call.receive<LoginReciveRemote>()

            val user = InMemoryCache.userList.firstOrNull { it.login == receive.login }

            if (user == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid login")
            }
            else {

                if (user.password == receive.password) {
                    val token = UUID.randomUUID().toString()
                    InMemoryCache.tokenList.add(TokenCache(login = receive.login, token = token))
                    call.respond(LoginResponseRemote(token = token))
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Invalid password")
                }
            }

        }
        get("/login"){
            call.respond(InMemoryCache.userList)
        }
    }
}