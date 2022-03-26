package com.gravitycode.bitcoinraffle.raffle

import com.gravitycode.bitcoinraffle.user.User

data class RaffleUiState(
    val participants: List<User> = listOf(),
    var winner: User? = null
)
