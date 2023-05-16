package com.DBshowroom.cache

import com.DBshowroom.features.register.RegisterReciveRemote

data class TokenCache(
    val login: String,
    val token: String
)

object InMemoryCache {
    val userList: MutableList<RegisterReciveRemote> = mutableListOf()
    val tokenList: MutableList<TokenCache> = mutableListOf()
}