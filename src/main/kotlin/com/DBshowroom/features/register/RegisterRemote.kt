package com.DBshowroom.features.register

import kotlinx.serialization.Serializable

@Serializable
data class RegisterReciveRemote(
    val login: String,
    val name: String,
    val password: String
)

@Serializable
data class RegisterResponseRemote(
    val token: String
)