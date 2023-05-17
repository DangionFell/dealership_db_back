package com.DBshowroom.features.routing


import com.DBshowroom.database.manager.Manager
import com.DBshowroom.database.manager.ManagerLoginReceiveRemote
import com.DBshowroom.database.manager.ManagerRegisterReceiveRemote
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureManagerRouting() {
    routing {
        get("/manager") {
            call.respond(Manager.readAll())
        }

        get("/manager/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid id")
            } else {
                val manager = Manager.read(id)
                if (manager != null) {
                    call.respond(manager)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Manager not found")
                }
            }
        }

        post("/manager/register") {
            val request = call.receive<ManagerRegisterReceiveRemote>()
            if (request == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid request")
            } else {
                if (Manager.findByPhoneAndPassword(ManagerLoginReceiveRemote(request.phone, request.password)) == null) {
                    val managerId = Manager.create(request)
                    call.respond(managerId)
                } else {
                    call.respond(HttpStatusCode.Conflict, "User already exists")
                }
            }
        }

        post("/manager/login") {
            val request = call.receive<ManagerLoginReceiveRemote>()
            if (request == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid request")
            } else {
                val manager = Manager.findByPhoneAndPassword(request)
                if (manager != null) {
                    call.respond(manager)
                } else {
                    call.respond(HttpStatusCode.Conflict, "Wrong phone or password")
                }
            }
        }

    }
}