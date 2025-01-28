package com.example.uasa1.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasa1.model.Proyek
import com.example.uasa1.repository.ProyekRepository
import kotlinx.coroutines.launch

class DetailProyekViewModel(private val proyekRepository: ProyekRepository) : ViewModel() {

    var detailProyekUiState by mutableStateOf(DetailProyekUiState())
        private set

    fun getProyekDetail(id_proyek: Int) {
        viewModelScope.launch {
            detailProyekUiState = DetailProyekUiState(isLoading = true)
            try {
                val proyekDetailResponse = proyekRepository.getProyekById(id_proyek)
                if (proyekDetailResponse.status) {
                    detailProyekUiState = DetailProyekUiState(proyekDetailEvent = proyekDetailResponse.data.toProyekDetailEvent())
                } else {
                    detailProyekUiState = DetailProyekUiState(isError = true, errorMessage = proyekDetailResponse.message)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                detailProyekUiState = DetailProyekUiState(isError = true, errorMessage = "Failed to fetch details: ${e.message}")
            }
        }
    }
}

data class DetailProyekUiState(
    val proyekDetailEvent: InsertProyekEvent = InsertProyekEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
) {
    val isProyekDetailEventNotEmpty: Boolean
        get() = proyekDetailEvent != InsertProyekEvent()
}

fun Proyek.toProyekDetailEvent(): InsertProyekEvent {
    return InsertProyekEvent(
        id_proyek = id_proyek,
        nama_proyek = nama_proyek,
        deskripsi_proyek = deskripsi_proyek,
        tanggal_mulai = tanggal_mulai,
        tanggal_berakhir = tanggal_berakhir,
        status_proyek = status_proyek
    )
}
