package com.example.uasa1.ui.viewmodel

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasa1.model.Tugas
import com.example.uasa1.repository.TugasRepository
import kotlinx.coroutines.launch
import okio.IOException


sealed class TugasUiState {
    data class Success(val tugas: List<Tugas>) : TugasUiState()
    object Error : TugasUiState()
    object Loading : TugasUiState()
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class HomeTugasViewModel(private val tugasRepository: TugasRepository) : ViewModel() {
    var tugasUiState: TugasUiState by mutableStateOf(TugasUiState.Loading)
        private set

    init {
        getTugas()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getTugas() {
        viewModelScope.launch {
            tugasUiState = TugasUiState.Loading
            tugasUiState = try {
                TugasUiState.Success(tugasRepository.getAllTugas().data) // Assuming getAllTugas returns TugasResponse
            } catch (e: IOException) {
                TugasUiState.Error
            } catch (e: HttpException) {
                TugasUiState.Error
            }
        }
    }

    fun deleteTugas(idTugas: Int) {
        viewModelScope.launch {
            try {
                println("Deleting tugas with id: $idTugas") // Debug log
                tugasRepository.deleteTugas(id_tugas = idTugas)
                println("Tugas deleted successfully") // Debug log
                getTugas() // Refresh the list of tugas after deletion
            } catch (e: Exception) {
                println("Error deleting tugas: ${e.message}") // Debug log
                tugasUiState = TugasUiState.Error
            }
        }
    }
}
