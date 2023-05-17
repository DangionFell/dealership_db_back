package com.DBshowroom.database.contract

import kotlinx.serialization.Serializable

@Serializable
data class ContractDTO(
    val id: Int,
    val date: String,
    val showroomId: Int,
    val carId: Int,
    val clientId: Int,
    val managerId: Int,
)

@Serializable
data class ContractReceiveRemote(
    val date: String,
    val showroomId: Int,
    val carId: Int,
    val clientId: Int,
    val managerId: Int,
)