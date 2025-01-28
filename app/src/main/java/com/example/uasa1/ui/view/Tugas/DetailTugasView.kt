package com.example.uasa1.ui.view.Tugas

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uasa1.ui.navigation.DestinasiNavigasi
import com.example.uasa1.ui.viewmodel.DetailTugasViewModel
import com.example.uasa1.ui.viewmodel.PenyediaViewModel

object DestinasiDetailTugas : DestinasiNavigasi {
    override val route = "detail_tugas"
    override val titleRes = "Detail Tugas"
    const val ID_TUGAS = "id_tugas"
    val routeWithArgs = "$route/{$ID_TUGAS}"
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTugasScreen(
    id_tugas: Int,
    onEditClick: (String) -> Unit = { },
    onBackClick: () -> Unit = { },
    modifier: Modifier = Modifier,
    viewModel: DetailTugasViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val tugas = viewModel.tugasDetailUiState.tugasDetailEvent

    LaunchedEffect(id_tugas) {
        viewModel.TugasDetail(id_tugas = id_tugas.toInt())
    }

    val isLoading = viewModel.tugasDetailUiState.isLoading
    val isError = viewModel.tugasDetailUiState.isError
    val errorMessage = viewModel.tugasDetailUiState.errorMessage

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(DestinasiDetailTugas.titleRes) },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
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
                } else if (tugas != null) {
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
                                // Use Row for each detail with label and value aligned
                                DetailRowTugas(label = "ID Tugas", value = tugas.id_tugas.toString())
                                DetailRowTugas(label = "Nama Tugas", value = tugas.nama_tugas)
                                DetailRowTugas(label = "Deskripsi Tugas", value = tugas.deskripsi_tugas)
                                DetailRowTugas(label = "Prioritas", value = tugas.prioritas)
                                DetailRowTugas(label = "Status Tugas", value = tugas.status_tugas)
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(onClick = { onEditClick(tugas.id_tugas.toString()) }) {
                                Text("Edit Data")
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun DetailRowTugas(label: String, value: String) {
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
