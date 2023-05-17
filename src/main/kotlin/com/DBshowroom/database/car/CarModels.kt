package com.DBshowroom.database.car

import kotlinx.serialization.Serializable

@Serializable
data class CarDTO (
    val id: Int,
    val model: String,
    val config: String,
    val yearOfManufacture :String,
    val horsepower :Int,
    val price :Int,
    val color :String,
    val state :String,
    val showroomId :Int
)

@Serializable
data class CarReceiveRemote(
    val model: String,
    val config: String,
    val yearOfManufacture :String,
    val horsepower :Int,
    val price :Int,
    val color :String,
    val state :String,
    val showroomId :Int
)