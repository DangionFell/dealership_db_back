package com.DBshowroom.features.routing

import com.DBshowroom.database.showroom.Showroom
import com.DBshowroom.database.showroom.ShowroomReceiveRemote
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureShowroomRouting() {
    routing {
        get("/showroom") {
            call.respond(Showroom.readAll())
        }
        get("/showroom/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid id")
            } else {
                val showroom = Showroom.read(id)
                if (showroom != null) {
                    call.respond(showroom)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Showroom not found")
                }
            }
        }
        post("/showroom"){
            val request = call.receive<ShowroomReceiveRemote>()
            if (request == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid request")
            } else {
                if (Showroom.getShowroomByAddress(request.address) == null) {
                    val showroomId = Showroom.create(request)
                    call.respond(HttpStatusCode.Created, "Showroom created with id: $showroomId")
                }
            }
        }
    }
}