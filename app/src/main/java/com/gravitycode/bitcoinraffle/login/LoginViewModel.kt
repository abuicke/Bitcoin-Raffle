package com.gravitycode.bitcoinraffle.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gravitycode.bitcoinraffle.user.User
import com.gravitycode.bitcoinraffle.user.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val usersRepository: UsersRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState> = _state.asStateFlow()

    fun login(name: String, btcAddress: String) {
        viewModelScope.launch {
            usersRepository.setLoggedInUser(User(name, btcAddress))
            _state.emit(LoginUiState(name, btcAddress, Login.LOGGED_IN))
        }
    }
}