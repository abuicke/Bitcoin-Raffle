package com.gravitycode.bitcoinraffle.users

import android.content.SharedPreferences
import com.google.common.base.Preconditions
import com.gravitycode.bitcoinraffle.nearby.DiscoverUsersService
import com.gravitycode.bitcoinraffle.util.Bitcoin
import com.gravitycode.bitcoinraffle.util.StrictSharedPreferences
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val discoverUsersService: DiscoverUsersService
) {

    private companion object {
        const val USERNAME = "signedInUser-name"
        const val BTC_WALLET = "signedInUser-btcWallet"
    }

    private var loggedInUser: User? = null

    init {
        val strictSharedPreferences = StrictSharedPreferences(sharedPreferences)

        val loggedInUserNameExists = sharedPreferences.contains(USERNAME)
        val loggedInUserWalletExists = sharedPreferences.contains(BTC_WALLET)

        if (loggedInUserNameExists && loggedInUserWalletExists) {
            val name = strictSharedPreferences.getString(USERNAME)
            val btcAddress = strictSharedPreferences.getString(BTC_WALLET)
            loggedInUser = User(name, btcAddress)
        }
    }

    /**
     * @return null if no user is logged in.
     * */
    fun getLoggedInUser(): User? = loggedInUser

    fun setLoggedInUser(user: User): Result<User> {
        val isValidName = user.name.isNotEmpty()
        val isValidBtcAddress = Bitcoin.isValidBtcWallet(user.btcWalletAddress)

        return if (isValidName && isValidBtcAddress) {
            sharedPreferences.edit()
                .putString(USERNAME, user.name)
                .putString(BTC_WALLET, user.btcWalletAddress)
                .apply()
            Result.success(user)
        } else if (!isValidName) {
            val throwable = Throwable("name must be at least one character long")
            Result.failure(throwable)
        } else {
            val throwable = Throwable("invalid bitcoin wallet address: ${user.btcWalletAddress}")
            Result.failure(throwable)
        }
    }

    /**
     * @return All users in the repository (including the logged in user) in no particular order.
     * @throws IllegalStateException if a logged in user hasn't been set
     * */
    fun getAllUsers(): Flow<User> {
        Preconditions.checkState(
            loggedInUser != null,
            "Current user must be logged in. " +
                    "Use ${javaClass.canonicalName}.setLoggedInUser(User) to set the current user."
        )

        return merge(
            MutableStateFlow(loggedInUser!!),
            discoverUsersService.discoverUsers()
        )
    }
}