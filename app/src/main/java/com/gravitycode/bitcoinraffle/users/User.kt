package com.gravitycode.bitcoinraffle.users

data class User(
    val name: String,
    val btcWalletAddress: String,
    var profilePicUri: String? = null
)