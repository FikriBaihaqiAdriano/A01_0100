package com.example.uasa1.ui.view.Anggota

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
import com.example.uasa1.ui.viewmodel.DetailAnggotaTimViewModel
import com.example.uasa1.ui.viewmodel.PenyediaViewModel

object DestinasiDetailAnggotaTim : DestinasiNavigasi {
    override val route = "detail_anggota"
    override val titleRes = "Detail Anggota Tim"
    const val ID_ANGGOTA = "id_anggota"
    val routeWithArgs = "$route/{$ID_ANGGOTA}"
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailAnggotaTimScreen(
    id_anggota: Int,
    onEditClick: (String) -> Unit = { },
    onBackClick: () -> Unit = { },
    modifier: Modifier = Modifier,
    viewModel: DetailAnggotaTimViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val anggotaTim = viewModel.AnggotaTimDetailUiState.anggotaTimDetailEvent

    LaunchedEffect(id_anggota) {
        viewModel.AnggotaTimDetail(id_anggota = id_anggota.toInt())
    }

    val isLoading = viewModel.AnggotaTimDetailUiState.isLoading
    val isError = viewModel.AnggotaTimDetailUiState.isError
    val errorMessage = viewModel.AnggotaTimDetailUiState.errorMessage


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(DestinasiDetailAnggotaTim.titleRes) },
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
                }
                else if (isError) {
                    Text(
                        text = errorMessage,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else if (viewModel.AnggotaTimDetailUiState.isAnggotaTimDetailEventNotEmpty) {
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
                                DetailRowAnggota(label = "ID Anggota", value = anggotaTim.id_anggota.toString())
                                DetailRowAnggota(label = "ID Tim", value = anggotaTim.id_tim.toString())
                                DetailRowAnggota(label = "Nama Anggota", value = anggotaTim.nama_anggota)
                                DetailRowAnggota(label = "Peran", value = anggotaTim.peran)
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(onClick = { onEditClick(anggotaTim.id_anggota.toString()) }) {
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
fun DetailRowAnggota(label: String, value: String) {
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
