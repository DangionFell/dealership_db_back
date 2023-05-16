package com.DBshowroom.database.showroom

import kotlinx.serialization.Serializable

@Serializable
data class ShowroomDTO (
    val id: Int,
    val name: String,
    val address: String,
    val phone: String
)

@Serializable
data class ShowroomReceiveRemote(
    val name: String,
    val address: String,
    val phone: String
)