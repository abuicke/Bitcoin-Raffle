package com.gravitycode.bitcoinraffle.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gravitycode.bitcoinraffle.user.User
import com.gravitycode.bitcoinraffle.user.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val usersRepository: UsersRepository
) : ViewModel() {

    private val _uiStateStream = MutableSharedFlow<LoginUiState>()
    val uiStateStream: SharedFlow<LoginUiState> = _uiStateStream.asSharedFlow()

    fun login(name: String, btcAddress: String) {
        viewModelScope.launch {
            val user = User(name, btcAddress)
            val result = usersRepository.setLoggedInUser(user)
            if (result.isSuccess) {
                val uiState = LoginUiState(name, btcAddress, Login.LOGGED_IN)
                _uiStateStream.emit(uiState)
            } else {
                val throwable = result.exceptionOrNull()
                val uiState = LoginUiState(login = Login.LOGIN_FAILED, error = throwable)
                _uiStateStream.emit(uiState)
            }
        }
    }
}