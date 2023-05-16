package com.DBshowroom.features.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginReciveRemote(
    val login: String,
    val password: String
)

@Serializable
data class LoginResponseRemote(
    val token: String
)