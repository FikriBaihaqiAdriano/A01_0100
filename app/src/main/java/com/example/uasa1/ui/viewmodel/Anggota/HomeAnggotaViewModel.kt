package com.example.uasa1.ui.viewmodel

import android.annotation.SuppressLint
import android.net.http.HttpException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasa1.model.Anggota
import com.example.uasa1.repository.AnggotaTimRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class AnggotaTimUiState {
    data class Success(val anggotaTim: List<Anggota>) : AnggotaTimUiState()
    object Error : AnggotaTimUiState()
    object Loading : AnggotaTimUiState()
}

class HomeAnggotaTimViewModel(private val anggotaTimRepository: AnggotaTimRepository) : ViewModel() {
    var anggotaTimUiState: AnggotaTimUiState by mutableStateOf(AnggotaTimUiState.Loading)
        private set

    init {
        getAnggotaTim()
    }

    fun getAnggotaTim() {
        viewModelScope.launch {
            anggotaTimUiState = AnggotaTimUiState.Loading
            anggotaTimUiState = try {
                val response = anggotaTimRepository.getAllAnggotaTim()
                if (response.status) {
                    AnggotaTimUiState.Success(response.data)
                } else {
                    AnggotaTimUiState.Error
                }
            } catch (e: IOException) {
                AnggotaTimUiState.Error
            }
        }
    }

    fun deleteAnggotaTim(id_Anggota: Int) {
        viewModelScope.launch {
            try {
                anggotaTimRepository.deleteAnggotaTim(id_Anggota)
                getAnggotaTim() // Refresh data setelah penghapusan
            } catch (e: Exception) {
                anggotaTimUiState = AnggotaTimUiState.Error
            } catch (@SuppressLint("NewApi") e: HttpException) {
                AnggotaTimUiState.Error
            }
        }
    }
}