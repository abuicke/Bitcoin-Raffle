package com.gravitycode.bitcoinraffle.user

data class User(
    val name: String,
    val btcWalletAddress: String,
    var profilePicUri: String? = null
)