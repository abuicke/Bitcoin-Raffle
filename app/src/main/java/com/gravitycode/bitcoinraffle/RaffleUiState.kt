package com.gravitycode.bitcoinraffle

data class RaffleUiState(
    val participants: List<User> = listOf(),
    var winner: User? = null
)
