package com.example.uasa1.ui.viewmodel

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasa1.model.Proyek
import com.example.uasa1.repository.ProyekRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class ProyekUiState {
    data class Success(val proyek: List<Proyek>) : ProyekUiState()
    object Error : ProyekUiState()
    object Loading : ProyekUiState()
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class HomeProyekViewModel(private val proyekRepository: ProyekRepository) : ViewModel() {
    var proyekUIState: ProyekUiState by mutableStateOf(ProyekUiState.Loading)
        private set

    init {
        getProyek()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getProyek() {
        viewModelScope.launch {
            proyekUIState = ProyekUiState.Loading
            proyekUIState = try {
                ProyekUiState.Success(proyekRepository.getAllProyek().data)  // Assuming getProyek returns ProyekResponse
            } catch (e: IOException) {
                ProyekUiState.Error
            } catch (e: HttpException) {
                ProyekUiState.Error
            }
        }
    }

    fun deleteProyek(idProyek: Int) {
        viewModelScope.launch {
            try {
                println("Deleting proyek with id: $idProyek") // Debug log
                proyekRepository.deleteProyek(idProyek)
                println("Proyek deleted successfully") // Debug log
                getProyek()  // Refresh the list of projects after deletion
            } catch (e: Exception) {
                println("Error deleting proyek: ${e.message}") // Debug log
                proyekUIState = ProyekUiState.Error
            }
        }
    }
}
