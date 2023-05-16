package com.DBshowroom.database.client

import kotlinx.serialization.Serializable

@Serializable
data class ClientDTO(
    val id: Int,
    val name: String,
    val phone: String,
    val password: String
)

@Serializable
data class ClientLoginReceiveRemote(
    val phone: String,
    val password: String
)

@Serializable
data class ClientLoginResponseRemote(
    val name: String,
    val phone: String,
    val password: String
)

@Serializable
data class ClientRegisterReceiveRemote(
    val name: String,
    val phone: String,
    val password: String
)




