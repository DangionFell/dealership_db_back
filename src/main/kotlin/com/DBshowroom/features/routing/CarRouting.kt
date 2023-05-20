package com.DBshowroom.features.routing

import com.DBshowroom.database.car.Car
import com.DBshowroom.database.car.CarReceiveRemote
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureCarRouting() {
    routing {
        get("/cars") {
            call.respond(Car.readAll())
        }

        get("/car/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid id")
            } else {
                val car = Car.read(id)
                if (car != null) {
                    call.respond(car)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Car not found")
                }
            }
        }

        get("car/client/{phone}") {
            val phone = call.parameters["phone"]
            if (phone == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid phone")
            } else {
                val cars = Car.getCarsByClientPhone(phone)
                if (cars != null) {
                    call.respond(cars)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Cars not found")
                }
            }
        }

        get("car/manager/{phone}") {
            val phone = call.parameters["phone"]
            if (phone == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid phone")
            } else {
                val cars = Car.getCarsByManagerPhone(phone)
                if (cars != null) {
                    call.respond(cars)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Cars not found")
                }
            }
        }

        get("/showroom/{id}/cars"){
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid id")
            } else {
                val cars = Car.getCarsByShowroomId(id)
                if (cars != null) {
                    call.respond(cars)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Showroom not found or empty")
                }
            }
        }

        get("/showroom/{id}/cars/in_stock"){
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid id")
            } else {
                val cars = Car.getCarsInStockByShowroomId(id)
                if (cars != null) {
                    call.respond(cars)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Showroom not found or empty")
                }
            }
        }

        post("/car"){
            val request = call.receive<CarReceiveRemote>()
            if (request == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid request")
            } else {
                val carId = Car.create(request)
                call.respond(carId)
            }
        }
    }
}