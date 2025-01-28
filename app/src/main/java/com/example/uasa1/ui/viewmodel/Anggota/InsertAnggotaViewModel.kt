package com.example.uasa1.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasa1.model.Anggota
import com.example.uasa1.repository.AnggotaTimRepository
import kotlinx.coroutines.launch

class InsertAnggotaTimViewModel(private val anggotaTimRepository: AnggotaTimRepository) : ViewModel() {
    var insertAnggotaTimUiState by mutableStateOf(InsertAnggotaTimUiState())
        private set

    fun updateInsertAnggotaTimState(insertAnggotaTimEvent: InsertAnggotaTimEvent) {
        insertAnggotaTimUiState = InsertAnggotaTimUiState(insertAnggotaTimEvent = insertAnggotaTimEvent)
    }

    fun insertAnggotaTim() {
        viewModelScope.launch {
            try {
                anggotaTimRepository.insertAnggotaTim(insertAnggotaTimUiState.insertAnggotaTimEvent.toAnggotaTim())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertAnggotaTimUiState(
    val insertAnggotaTimEvent: InsertAnggotaTimEvent = InsertAnggotaTimEvent()
)

data class InsertAnggotaTimEvent(
    val id_anggota: Int = 0,
    val id_tim: Int = 0,
    val nama_anggota: String = "",
    val peran: String = ""
)

fun InsertAnggotaTimEvent.toAnggotaTim(): Anggota = Anggota(
    id_anggota = id_anggota,
    id_tim = id_tim,
    nama_anggota = nama_anggota,
    peran = peran
)

fun Anggota.toUiStateAnggotaTim(): InsertAnggotaTimUiState = InsertAnggotaTimUiState(
    insertAnggotaTimEvent = toInsertAnggotaTimEvent()
)

fun Anggota.toInsertAnggotaTimEvent(): InsertAnggotaTimEvent = InsertAnggotaTimEvent(
    id_anggota = id_anggota,
    id_tim = id_tim,
    nama_anggota = nama_anggota,
    peran = peran
)
