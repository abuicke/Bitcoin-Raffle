package com.gravitycode.bitcoinraffle.app

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.gravitycode.bitcoinraffle.login.Login
import com.gravitycode.bitcoinraffle.login.LoginView
import com.gravitycode.bitcoinraffle.login.LoginViewModel
import com.gravitycode.bitcoinraffle.raffle.Raffle
import com.gravitycode.bitcoinraffle.raffle.RaffleView
import com.gravitycode.bitcoinraffle.raffle.RaffleViewModel
import com.gravitycode.bitcoinraffle.users.User
import com.gravitycode.bitcoinraffle.users.UsersRepository
import com.gravitycode.bitcoinraffle.util.showToast
import com.gravitycode.bitcoinraffle.view.IView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * desktop wallet (testnet)     2MuUL489qeTWB9KSqbWnVLP1oU2V5AUGwHa
 * phone wallet (testnet)       tb1qhg2xc5mvx6ug339yj4pp532ktyfhdmt7ufhel3
 * faucet return address        mv4rnyY3Su5gjcDNzbMLKBQkBicCtHUtFB
 *
 * TODO: Text Fields and Enter Button should move up when keyboard displayed.
 * TODO: Introduce Abstract IView parent to share boiler plate between View classes.
 * TODO: Add enter button on keyboard for login.
 * TODO: Add Sign Out and Settings options in ActionBar Overflow Menu.
 * TODO: Bitcoin Wallet Login Field should keep going horizontally, not wrap.
 * TODO: Center text in error message toast.
 * TODO: Shouldn't be using Throwable message as Toast text in LOGIN_FAILED handling.
 * TODO: Write Unit Tests For Login View and Associated Functions.
 * TODO: Put Login Screen in ScrollView (doesn't show wallet field when in landscape orientation).
 * TODO: Add check for google play services https://developers.google.com/android/reference/com/google/android/gms/common/GoogleApiAvailability#isGooglePlayServicesAvailable(android.content.Context)
 * TODO: Add warning popup at the beginning of the app with the same warning as bitcoinj that the
 * risk is entirely on the user. This app is for entertainment purposes only. Only raffle small amounts.
 * TODO: Add Timber.bug(String) if possible. To create an easy call when adding logs to track a
 * particular bug which will be removed once the bug is found. TAG = "bug"
 * TODO: Can unnecessary parts of libraries be stripped out for the APK? Proguard?
 * TODO: Attach a fee from each raffle https://bitcoinj.org/getting-started-java#customizing-the-sending-process-and-setting-fees
 * The smallest amount possible. Something like 1 cent.
 * TODO: Make sure raffle stake is kept below 0.0009 BTC Use TextWatcher and prevent editing prefilled 0.000?
 * TODO: Raffle View should scroll on landscape orientation (is there a way to do this without scrollview)
 * TODO: Show BTC price in local currency when typing stake
 * TODO: Make sure back button functions correctly
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

    @Inject lateinit var usersRepository: UsersRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val loggedInUser: User? = usersRepository.getLoggedInUser()

        if (loggedInUser != null) {
            showRaffleView()
            setToolbarTitle(loggedInUser.btcWalletAddress)
        } else {
            showLoginView()
        }
    }

    fun setView(iView: IView<out Any>) {
        setContentView(iView.contentView)
        setSupportActionBar(iView.toolbar)
    }

    fun setToolbarTitle(title: CharSequence) {
        checkNotNull(supportActionBar)
        supportActionBar!!.title = title
    }

    fun displayMessage(msg: CharSequence) {
        showToast(msg)
    }

    fun displayError(errMsg: CharSequence) {
        showToast(errMsg)
    }

    fun showLoginView() {
        loginView.setEventListener { loginViewEvent ->
            val name = loginViewEvent.name
            val btcAddress = loginViewEvent.btcAddress
            loginViewModel.login(name, btcAddress)
        }

        awaitLogin()
        setView(loginView)
    }

    /**
     * Must be called in [onCreate].
     *
     * @see repeatOnLifecycle for further explanation, i.e. "The best practice is to call this
     * function when the lifecycle is initialized. For example, [android.app.Activity.onCreate]
     * in an Activity, or [androidx.fragment.app.Fragment.onViewCreated] in a Fragment."
     * */
    fun awaitLogin() {
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
        raffleView.setEventListener { raffleViewEvent ->
            when (raffleViewEvent.raffle) {
                Raffle.STARTED -> raffleViewModel.startRaffle()
                Raffle.FINISHED -> Unit
            }
        }

        watchRaffle()
        setView(raffleView)
    }

    /**
     * Must be called in [onCreate].
     *
     * @see repeatOnLifecycle for further explanation, i.e. "The best practice is to call this
     * function when the lifecycle is initialized. For example, [android.app.Activity.onCreate]
     * in an Activity, or [androidx.fragment.app.Fragment.onViewCreated] in a Fragment."
     * */
    fun watchRaffle() {
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