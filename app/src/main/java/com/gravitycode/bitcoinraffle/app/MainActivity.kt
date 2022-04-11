package com.gravitycode.bitcoinraffle.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.gravitycode.bitcoinraffle.bitcoin.Bitcoin
import com.gravitycode.bitcoinraffle.login.GetLoginStateUseCase
import com.gravitycode.bitcoinraffle.login.Login
import com.gravitycode.bitcoinraffle.login.LoginView
import com.gravitycode.bitcoinraffle.login.LoginViewModel
import com.gravitycode.bitcoinraffle.raffle.Raffle
import com.gravitycode.bitcoinraffle.raffle.RaffleView
import com.gravitycode.bitcoinraffle.raffle.RaffleViewModel
import com.gravitycode.bitcoinraffle.util.showToast
import com.gravitycode.bitcoinraffle.view.IView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.bitcoinj.utils.BriefLogFormatter
import timber.log.Timber
import javax.inject.Inject

/**
 * TODO: Since ACCESS_FINE_LOCATION, BLUETOOTH_ADVERTISE, BLUETOOTH_CONNECT, BLUETOOTH_SCAN and
 * READ_EXTERNAL_STORAGE are considered to be dangerous system permissions, in addition to
 * adding them to your manifest, you must request these permissions at runtime, as described
 * in Requesting Permissions. https://developer.android.com/training/permissions/requesting.html
 * If the user does not grant all required permissions for the Strategy you plan to use, the
 * Nearby Connections API will refuse to allow your app to start advertising or discovering.
 * */
@Suppress("MemberVisibilityCanBePrivate")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private val raffleViewModel: RaffleViewModel by viewModels()

    @Inject lateinit var loginView: LoginView
    @Inject lateinit var raffleView: RaffleView

    @Inject lateinit var getLoginStateUseCase: GetLoginStateUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val loginState = getLoginStateUseCase()

        if (loginState == Login.LOGGED_IN) {
            showRaffleView()
        } else {
            showLoginView()
        }
    }

    fun setContentView(iView: IView<out Any>) {
        val contentView = iView.getContentView()
        super.setContentView(contentView)
    }

    fun displayMessage(msg: CharSequence) {
        showToast(msg)
    }

    fun displayError(errMsg: CharSequence) {
        showToast(errMsg)
    }

    fun showLoginView() {
        /**
         * TODO: This initialization should take place once outside of the `show` logic?
         * */
        loginView.setEventListener { loginViewEvent ->
            val name = loginViewEvent.name
            val btcAddress = loginViewEvent.btcAddress
            loginViewModel.login(name, btcAddress)
        }

        waitForLogin()
        setContentView(loginView)
    }

    /**
     * Must be called in [onCreate].
     *
     * @see repeatOnLifecycle for further explanation, i.e. "The best practice is to call this
     * function when the lifecycle is initialized. For example, [android.app.Activity.onCreate]
     * in an Activity, or [androidx.fragment.app.Fragment.onViewCreated] in a Fragment."
     * */
    fun waitForLogin() {
        var loginJob: Job? = null
        loginJob = lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.uiState.collect { loginUiState ->
                    when (loginUiState.login) {
                        Login.LOGGED_IN -> {
                            Timber.i(
                                "logged in as [${loginUiState.name}, " +
                                        "${loginUiState.btcWalletAddress}]"
                            )
                            showRaffleView()
                            loginJob!!.cancel()
                        }
                        Login.NOT_LOGGED_IN -> Unit
                        Login.LOGIN_IN_PROGRESS -> {
                            Timber.i(
                                "logging in as [${loginUiState.name}, " +
                                        "${loginUiState.btcWalletAddress}]"
                            )
                        }
                        Login.LOGIN_FAILED -> {
                            val errMsg = loginUiState.error?.message
                            displayError(errMsg ?: "Login Failed")
                            Timber.w("login failed: $errMsg", loginUiState.error)
                        }
                    }
                }
            }
        }
    }

    fun showRaffleView() {
        /**
         * TODO: This initialization should take place once outside of the `show` logic?
         * */
        raffleView.setEventListener { raffleViewEvent ->
            when (raffleViewEvent.raffle) {
                Raffle.STARTED -> raffleViewModel.startRaffle()
                Raffle.FINISHED -> Unit
            }
        }

        watchRaffle(raffleView)
        setContentView(raffleView)
    }

    /**
     * Must be called in [onCreate].
     *
     * @see repeatOnLifecycle for further explanation, i.e. "The best practice is to call this
     * function when the lifecycle is initialized. For example, [android.app.Activity.onCreate]
     * in an Activity, or [androidx.fragment.app.Fragment.onViewCreated] in a Fragment."
     * */
    fun watchRaffle(raffleView: RaffleView) {
        var raffleJob: Job? = null
        raffleJob = lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                raffleViewModel.uiState.collect { raffleUiState ->
                    raffleView.displayUsers(raffleUiState.users)
                    if (raffleUiState.winner != null) {
                        displayMessage("Winner! ${raffleUiState.winner!!.name}")
                        raffleJob!!.cancel()
                    }
                }
            }
        }
    }
}