package com.example.uasa1.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasa1.model.Proyek
import com.example.uasa1.repository.ProyekRepository
import kotlinx.coroutines.launch

class InsertProyekViewModel(private val proyekRepository: ProyekRepository) : ViewModel() {
    var insertProyekUiState by mutableStateOf(InsertProyekUiState())
        private set

    fun updateInsertProyekState(insertProyekEvent: InsertProyekEvent) {
        insertProyekUiState = InsertProyekUiState(insertProyekEvent = insertProyekEvent)
    }

    fun insertProyek() {
        viewModelScope.launch {
            try {
                proyekRepository.insertProyek(insertProyekUiState.insertProyekEvent.toProyek())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertProyekUiState(
    val insertProyekEvent: InsertProyekEvent = InsertProyekEvent()
)

data class InsertProyekEvent(
    val id_proyek: Int = 0,
    val nama_proyek: String = "",
    val deskripsi_proyek: String = "",
    val tanggal_mulai: String = "",
    val tanggal_berakhir: String = "", // Ganti tanggal_selesai menjadi tanggal_berakhir
    val status_proyek: String = ""
)

fun InsertProyekEvent.toProyek(): Proyek = Proyek(
    id_proyek = id_proyek,
    nama_proyek = nama_proyek,
    deskripsi_proyek = deskripsi_proyek,
    tanggal_mulai = tanggal_mulai,
    tanggal_berakhir = tanggal_berakhir, // Ganti tanggal_selesai menjadi tanggal_berakhir
    status_proyek = status_proyek
)

fun Proyek.toUiStateProyek(): InsertProyekUiState = InsertProyekUiState(
    insertProyekEvent = toInsertProyekEvent()
)

fun Proyek.toInsertProyekEvent(): InsertProyekEvent = InsertProyekEvent(
    id_proyek = id_proyek,
    nama_proyek = nama_proyek,
    deskripsi_proyek = deskripsi_proyek,
    tanggal_mulai = tanggal_mulai,
    tanggal_berakhir = tanggal_berakhir, // Ganti tanggal_selesai menjadi tanggal_berakhir
    status_proyek = status_proyek
)
