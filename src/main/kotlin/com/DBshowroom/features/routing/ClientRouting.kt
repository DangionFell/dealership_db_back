package com.DBshowroom.features.routing

import com.DBshowroom.database.client.Client
import com.DBshowroom.database.client.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureClientRouting() {
    routing {
        get("/client") {
            call.respond(Client.readAll())
        }

        get("/client/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid id")
            } else {
                val client = Client.read(id)
                if (client != null) {
                    call.respond(client)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Client not found")
                }
            }
        }

        post("/client/register"){
            val request = call.receive<ClientRegisterReceiveRemote>()
            if (request == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid request")
            } else {
                if(Client.findByPhoneAndPassword(ClientLoginReceiveRemote(request.phone, request.password)) == null){
                    val clientId = Client.create(request)
                    call.respond(clientId)
                } else {
                    call.respond(HttpStatusCode.Conflict, "User already exists")
                }
            }
        }

        post("/client/login"){
            val request = call.receive<ClientLoginReceiveRemote>()
            if (request == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid request")
            } else {
                val client = Client.findByPhoneAndPassword(request)
                if(client != null){
                    call.respond(client)
                } else {
                    call.respond(HttpStatusCode.Conflict, "Wrong phone or password")
                }
            }
        }
    }
}