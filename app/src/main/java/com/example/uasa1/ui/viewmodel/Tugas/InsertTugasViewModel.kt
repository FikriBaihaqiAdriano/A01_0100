package com.example.uasa1.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasa1.model.Tugas
import com.example.uasa1.repository.TugasRepository
import kotlinx.coroutines.launch


class InsertTugasViewModel(private val tugasRepository: TugasRepository) : ViewModel() {
    var insertTugasUiState by mutableStateOf(InsertTugasUiState())
        private set

    fun updateInsertTugasState(insertTugasEvent: InsertTugasEvent) {
        insertTugasUiState = InsertTugasUiState(insertTugasEvent = insertTugasEvent)
    }

    fun insertTugas() {
    viewModelScope.launch {
        try {
            tugasRepository.insertTugas(insertTugasUiState.insertTugasEvent.toTugas())
        } catch (e: Exception) {
            e.printStackTrace()
            }
        }
    }
}

data class InsertTugasUiState(
    val insertTugasEvent: InsertTugasEvent = InsertTugasEvent()
)

data class InsertTugasEvent(
    val id_tugas: Int = 0,
    val id_proyek: Int = 0,
    val id_tim: Int = 0,
    val nama_tugas: String = "",
    val deskripsi_tugas: String = "",
    val prioritas: String = "",
    val status_tugas: String = ""
)

fun InsertTugasEvent.toTugas(): Tugas = Tugas(
    id_tugas = id_tugas,
    id_proyek = id_proyek,
    id_tim = id_tim,
    nama_tugas = nama_tugas,
    deskripsi_tugas = deskripsi_tugas,
    prioritas = prioritas,
    status_tugas = status_tugas
)

fun Tugas.toUiStateTugas(): InsertTugasUiState = InsertTugasUiState(
    insertTugasEvent = toInsertTugasEvent()
)

fun Tugas.toInsertTugasEvent(): InsertTugasEvent = InsertTugasEvent(
    id_tugas = id_tugas,
    id_proyek = id_proyek,
    id_tim = id_tim,
    nama_tugas = nama_tugas,
    deskripsi_tugas = deskripsi_tugas,
    prioritas = prioritas,
    status_tugas = status_tugas
)
