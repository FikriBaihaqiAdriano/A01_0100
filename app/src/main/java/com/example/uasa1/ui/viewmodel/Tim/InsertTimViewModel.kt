package com.example.uasa1.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasa1.model.Tim
import com.example.uasa1.repository.TimRepository
import kotlinx.coroutines.launch

class InsertTimViewModel(private val timRepository: TimRepository) : ViewModel() {
    var TInserUiState by mutableStateOf(InsertTimUiState())
        private set

    fun updateInsertTimState(insertTimEvent: InsertTimEvent) {
        TInserUiState = InsertTimUiState(insertTimEvent = insertTimEvent)
    }

    fun insertTim() {
        viewModelScope.launch {
            try {
                timRepository.insertTim(TInserUiState.insertTimEvent.toTim())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertTimUiState(
    val insertTimEvent: InsertTimEvent = InsertTimEvent()
)

data class InsertTimEvent(
    val id_tim: Int = 0,
    val nama_tim: String = "",
    val deskripsi_tim: String = ""
)

fun InsertTimEvent.toTim(): Tim = Tim(
    id_tim = id_tim,
    nama_tim = nama_tim,
    deskripsi_tim = deskripsi_tim
)

fun Tim.toUiStateTim(): InsertTimUiState = InsertTimUiState(
    insertTimEvent = toInsertTimEvent()
)

fun Tim.toInsertTimEvent(): InsertTimEvent = InsertTimEvent(
    id_tim = id_tim,
    nama_tim = nama_tim,
    deskripsi_tim = deskripsi_tim
)
