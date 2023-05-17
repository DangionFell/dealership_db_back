package com.DBshowroom.database.manager

import kotlinx.serialization.Serializable

@Serializable
data class ManagerDTO(
    val id: Int,
    val name: String,
    val phone: String,
    val password: String,
    val showroomId: Int
)

@Serializable
data class ManagerLoginReceiveRemote(
    val phone: String,
    val password: String
)

@Serializable
data class ManagerLoginResponseRemote(
    val name: String,
    val phone: String,
    val password: String
)

@Serializable
data class ManagerRegisterReceiveRemote(
    val name: String,
    val phone: String,
    val password: String,
    val showroomId: Int
)