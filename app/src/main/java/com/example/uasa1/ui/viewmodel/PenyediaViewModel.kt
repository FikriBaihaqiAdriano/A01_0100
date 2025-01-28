package com.example.uasa1.ui.viewmodel

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.uasa1.ManajemenApplications

object PenyediaViewModel {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    val Factory = viewModelFactory {
        // Tim
        initializer {
            HomeTimViewModel(
                manajemenApplications().ManajemenContainer.timRepository
            )
        }
        initializer {
            InsertTimViewModel(
                manajemenApplications().ManajemenContainer.timRepository
            )
        }
        initializer {
            DetailTimViewModel(
                manajemenApplications().ManajemenContainer.timRepository
            )
        }
        initializer {
            UpdateTimViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                timRepository = manajemenApplications().ManajemenContainer.timRepository
            )
        }

        // Proyek
        initializer {
            HomeProyekViewModel(
                manajemenApplications().ManajemenContainer.proyekRepository
            )
        }
        initializer {
            InsertProyekViewModel(
                manajemenApplications().ManajemenContainer.proyekRepository
            )
        }
        initializer {
            DetailProyekViewModel(
                manajemenApplications().ManajemenContainer.proyekRepository
            )
        }
        initializer {
            UpdateProyekViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                proyekRepository = manajemenApplications().ManajemenContainer.proyekRepository
            )
        }

        // Anggota Tim
        initializer {
            HomeAnggotaTimViewModel(
                manajemenApplications().ManajemenContainer.anggotaTimRepository
            )
        }
        initializer {
            InsertAnggotaTimViewModel(
                manajemenApplications().ManajemenContainer.anggotaTimRepository
            )
        }
        initializer {
            DetailAnggotaTimViewModel(
                manajemenApplications().ManajemenContainer.anggotaTimRepository
            )
        }
        initializer {
            UpdateAnggotaTimViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                anggotaTimRepository = manajemenApplications().ManajemenContainer.anggotaTimRepository
            )
        }

        // Tugas
        initializer {
            HomeTugasViewModel(
                manajemenApplications().ManajemenContainer.tugasRepository
            )
        }
        initializer {
            InsertTugasViewModel(
                manajemenApplications().ManajemenContainer.tugasRepository
            )
        }
        initializer {
            DetailTugasViewModel(
                manajemenApplications().ManajemenContainer.tugasRepository
            )
        }
        initializer {
            UpdateTugasViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                tugasRepository = manajemenApplications().ManajemenContainer.tugasRepository
            )
        }
    }
}

fun CreationExtras.manajemenApplications(): ManajemenApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ManajemenApplications)
