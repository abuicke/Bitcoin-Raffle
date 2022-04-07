package com.gravitycode.bitcoinraffle.nearby

import com.gravitycode.bitcoinraffle.users.User
import kotlinx.coroutines.flow.Flow

interface DiscoverUsersService {

    fun discoverUsers(): Flow<User>
}
