package com.example.uasa1

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uasa1.model.Proyek
import com.example.uasa1.model.Tim
import com.example.uasa1.ui.viewmodel.HomeProyekViewModel
import com.example.uasa1.ui.viewmodel.HomeTimViewModel
import com.example.uasa1.ui.viewmodel.PenyediaViewModel
import com.example.uasa1.ui.viewmodel.ProyekUiState
import com.example.uasa1.ui.viewmodel.TimUiState

object DataID {

}

object DataProyek {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    @Composable
    fun DataProyekList(
        HomeProyekViewModel: HomeProyekViewModel = viewModel(factory = PenyediaViewModel.Factory)
    ): List<Proyek> {
        // Mengakses proyekUIState secara langsung karena proyekUIState adalah mutableStateOf
        val proyekData = HomeProyekViewModel.proyekUIState

        return when (proyekData) {
            is ProyekUiState.Success -> proyekData.proyek
            else -> emptyList()
        }
    }
}

object DataTim {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    @Composable
    fun DataTimList(
        HomeTimViewModel: HomeTimViewModel = viewModel(factory = PenyediaViewModel.Factory)
        ): List<Tim> {
        // Mengakses timUIState secara langsung karena timUIState adalah mutableStateOf
        val timData = HomeTimViewModel.timUIState

        return when (timData) {
            is TimUiState.Success -> timData.tim
            else -> emptyList()
        }
    }
}


