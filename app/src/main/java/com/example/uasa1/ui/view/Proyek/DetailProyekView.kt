package com.example.uasa1.ui.view.Proyek

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uasa1.ui.navigation.DestinasiNavigasi
import com.example.uasa1.ui.viewmodel.DetailProyekViewModel
import com.example.uasa1.ui.viewmodel.PenyediaViewModel

object DestinasiDetailProyek : DestinasiNavigasi {
    override val route = "detail_proyek"
    override val titleRes = "Detail Proyek"
    const val ID_PROYEK = "id_proyek"
    val routeWithArgs = "$route/{$ID_PROYEK}"
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailProyekScreen(
    id_proyek: Int,
    onEditClick: (String) -> Unit = { },
    onBackClick: () -> Unit = { },
    onNavigateToHomeTugas: () -> Unit = { }, // Callback untuk navigasi ke HomeTugas
    modifier: Modifier = Modifier,
    viewModel: DetailProyekViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val proyek = viewModel.detailProyekUiState.proyekDetailEvent

    LaunchedEffect(id_proyek) {
        viewModel.getProyekDetail(id_proyek = id_proyek)
    }

    val isLoading = viewModel.detailProyekUiState.isLoading
    val isError = viewModel.detailProyekUiState.isError
    val errorMessage = viewModel.detailProyekUiState.errorMessage

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(DestinasiDetailProyek.titleRes) },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else if (isError) {
                    Text(
                        text = errorMessage,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else if (viewModel.detailProyekUiState.isProyekDetailEventNotEmpty) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(8.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                // Detail Proyek
                                DetailRowProyek(label = "ID Proyek", value = proyek.id_proyek.toString())
                                DetailRowProyek(label = "Nama Proyek", value = proyek.nama_proyek)
                                DetailRowProyek(label = "Deskripsi Proyek", value = proyek.deskripsi_proyek)
                                DetailRowProyek(label = "Tanggal Mulai", value = proyek.tanggal_mulai)
                                DetailRowProyek(label = "Tanggal Berakhir", value = proyek.tanggal_berakhir)
                                DetailRowProyek(label = "Status Proyek", value = proyek.status_proyek)
                            }
                        }

                        // Tombol Edit dan Navigasi ke HomeTugas
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(onClick = { onEditClick(proyek.id_proyek.toString()) }) {
                                Text("Edit Data")
                            }
                            Button(onClick = { onNavigateToHomeTugas() }) {
                                Text("Melihat Tugas-tugas") // Tombol menuju HomeTugas
                            }
                        }
                    }
                }
            }
        }
    )
}



@Composable
fun DetailRowProyek(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = ": $value",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(2f)
        )
    }
}
