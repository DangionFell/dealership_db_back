package com.DBshowroom.features.routing

import com.DBshowroom.database.contract.Contract
import com.DBshowroom.database.contract.ContractReceiveRemote
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureContractRouting() {
    routing {
        get("/contract") {
            call.respond(Contract.readAll())
        }

        get("/contract/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid id")
            } else {
                val contract = Contract.read(id)
                if (contract != null) {
                    call.respond(contract)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Contract not found")
                }
            }
        }

        post("/contract"){
            val request = call.receive<ContractReceiveRemote>()
            if (request == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid request")
            } else {
                val contractId = Contract.create(request)
                call.respond(HttpStatusCode.Created, "Contract created with id: $contractId")
            }
        }
    }
}