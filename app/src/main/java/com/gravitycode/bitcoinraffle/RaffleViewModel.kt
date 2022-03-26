package com.gravitycode.bitcoinraffle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
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

    private var fetchJob: Job? = null

    fun fetchRaffleParticipants() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            val participants = usersRepository.getAllUsers()
            _uiState.emit(RaffleUiState(participants = participants))
        }
    }

}