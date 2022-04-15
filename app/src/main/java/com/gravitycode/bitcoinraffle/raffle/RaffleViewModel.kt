package com.gravitycode.bitcoinraffle.raffle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gravitycode.bitcoinraffle.users.User
import com.gravitycode.bitcoinraffle.users.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RaffleViewModel @Inject constructor(
    private val usersRepository: UsersRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RaffleUiState())
    val uiState: StateFlow<RaffleUiState> = _uiState.asStateFlow()

    fun startRaffle() {
        viewModelScope.launch {
            usersRepository.getAllUsers().collect(object : FlowCollector<User> {

                val allUsers = ArrayList<User>()

                @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
                override suspend fun emit(user: User) {
                    allUsers.add(user)
                    allUsers.shuffle()
                    delay(5000)
                    _uiState.value = RaffleUiState(allUsers.toList())

                    if (allUsers.size > 3) {
                        _uiState.value = RaffleUiState(allUsers, allUsers.first())
                    }
                }
            })
        }
    }
}