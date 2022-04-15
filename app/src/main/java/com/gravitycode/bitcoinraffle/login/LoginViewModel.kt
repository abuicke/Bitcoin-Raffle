package com.gravitycode.bitcoinraffle.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gravitycode.bitcoinraffle.users.User
import com.gravitycode.bitcoinraffle.users.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val usersRepository: UsersRepository
) : ViewModel() {

    private val _uiState = MutableSharedFlow<LoginUiState>()
    val uiState: SharedFlow<LoginUiState> = _uiState.asSharedFlow()

    fun login(name: String, btcAddress: String) {
        viewModelScope.launch {
            val loginInProgress = LoginUiState(name, btcAddress, Login.LOGIN_IN_PROGRESS)
            _uiState.emit(loginInProgress)
            val user = User(name, btcAddress)
            val result = usersRepository.setLoggedInUser(user)
            if (result.isSuccess) {
                val loggedIn = LoginUiState(name, btcAddress, Login.LOGGED_IN)
                _uiState.emit(loggedIn)
            } else {
                val throwable = result.exceptionOrNull()
                val loginFailed = LoginUiState(login = Login.LOGIN_FAILED, error = throwable)
                _uiState.emit(loginFailed)
            }
        }
    }
}