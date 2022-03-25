package com.gravitycode.bitcoinraffle

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RaffleViewModel(
    private val usersRepository: UsersRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(RaffleUiState())
    val uiState: StateFlow<RaffleUiState> = _uiState.asStateFlow()
}