package com.gravitycode.bitcoinraffle.login

import com.gravitycode.bitcoinraffle.users.UsersRepository
import javax.inject.Inject

class GetLoginStateUseCase @Inject constructor(private val usersRepository: UsersRepository) {

    operator fun invoke() =
        if (usersRepository.getLoggedInUser() != null)
            Login.LOGGED_IN
        else
            Login.NOT_LOGGED_IN
}