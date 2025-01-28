package com.example.uasa1.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasa1.model.Tim
import com.example.uasa1.repository.TimRepository
import kotlinx.coroutines.launch

class DetailTimViewModel(private val timRepository: TimRepository) : ViewModel() {

    var TDetailUiState by mutableStateOf(TimDetailUiState())
        private set

    fun TimDetail(id_tim: Int) {
        viewModelScope.launch {
            TDetailUiState = TimDetailUiState(isLoading = true)
            try {
                val timDetailResponse = timRepository.getTimById(id_tim)
                if (timDetailResponse.status) {
                    TDetailUiState = TimDetailUiState(timDetailEvent = timDetailResponse.data.toTimDetailEvent())
                } else {
                    TDetailUiState = TimDetailUiState(isError = true, errorMessage = timDetailResponse.message)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                TDetailUiState = TimDetailUiState(isError = true, errorMessage = "Failed to fetch details: ${e.message}")
            }
        }
    }
}

data class TimDetailUiState(
    val timDetailEvent: InsertTimEvent = InsertTimEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
) {
    val isTimDetailEventNotEmpty: Boolean
        get() = timDetailEvent != InsertTimEvent()
}


fun Tim.toTimDetailEvent(): InsertTimEvent {
    return InsertTimEvent(
        id_tim = id_tim,
        nama_tim = nama_tim,
        deskripsi_tim = deskripsi_tim
    )
}
