package com.example.uasa1.ui.viewmodel

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasa1.model.Tim
import com.example.uasa1.repository.TimRepository
import kotlinx.coroutines.launch
import okio.IOException


sealed class TimUiState {
    data class Success(val tim: List<Tim>) : TimUiState()
    object Error : TimUiState()
    object Loading : TimUiState()
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class HomeTimViewModel(private val timRepository: TimRepository) : ViewModel() {
    var timUIState: TimUiState by mutableStateOf(TimUiState.Loading)
        private set

    init {
        getTim()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getTim() {
        viewModelScope.launch {
            timUIState = TimUiState.Loading
            timUIState = try {
                TimUiState.Success(timRepository.getAllTim().data)  // Assuming getTim returns TimResponse
            } catch (e: IOException) {
                TimUiState.Error
            } catch (e: HttpException) {
                TimUiState.Error
            }
        }
    }

    fun deletetim(idTim: Int) {
        viewModelScope.launch {
            try {
                println("Deleting tim with id: $idTim") // Debug log
                timRepository.deleteTim(idTim)
                println("Tim deleted successfully") // Debug log
                getTim()
            } catch (e: Exception) {
                println("Error deleting tim: ${e.message}") // Debug log
                timUIState = TimUiState.Error
            }
        }
    }

}
