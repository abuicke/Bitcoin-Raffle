package com.gravitycode.bitcoinraffle.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.gravitycode.bitcoinraffle.login.GetLoginStateUseCase
import com.gravitycode.bitcoinraffle.login.Login
import com.gravitycode.bitcoinraffle.login.LoginView
import com.gravitycode.bitcoinraffle.login.LoginViewModel
import com.gravitycode.bitcoinraffle.raffle.RaffleView
import com.gravitycode.bitcoinraffle.view.IView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("MemberVisibilityCanBePrivate")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels()

    @Inject lateinit var getLoginStateUseCase: GetLoginStateUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val loginState = getLoginStateUseCase()

        if (loginState == Login.LOGGED_IN) {
            showRaffleView()
        } else {
            waitForLogin()
            showLoginView()
        }
    }

    fun setContentView(iView: IView<out Any>) {
        super.setContentView(iView.contentView)
    }

    fun displayError() {
        Toast.makeText(baseContext, "ERROR!!!!", Toast.LENGTH_LONG).show()
    }

    fun showLoginView() {
        val loginView = LoginView(layoutInflater)
        loginView.setEventListener { loginViewEvent ->
            val name = loginViewEvent.name
            val btcAddress = loginViewEvent.btcAddress
            loginViewModel.login(name, btcAddress)
        }

        setContentView(loginView)
    }

    /**
     * Must be called in OnCreate.
     * @see repeatOnLifecycle for further explanation, i.e.
     * "The best practice is to call this function when
     * the lifecycle is initialized. For example, onCreate
     * in an Activity, or onViewCreated in a Fragment."
     * */
    fun waitForLogin() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.state.collect { loginUiState ->
                    when (loginUiState.login) {
                        Login.LOGGED_IN -> showRaffleView()
                        Login.LOGIN_FAILED -> displayError()
                        Login.NOT_LOGGED_IN -> Unit
                    }
                }
            }
        }
    }

    fun showRaffleView() {
        setContentView(RaffleView(layoutInflater))
    }
}