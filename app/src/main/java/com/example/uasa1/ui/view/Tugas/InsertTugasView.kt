package com.example.uasa1.ui.view.Tugas

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uasa1.DataProyek
import com.example.uasa1.DataTim
import com.example.uasa1.ui.costumwidget.CostumeTopAppBar
import com.example.uasa1.ui.costumwidget.Dropdown
import com.example.uasa1.ui.navigation.DestinasiNavigasi
import com.example.uasa1.ui.viewmodel.InsertTugasEvent
import com.example.uasa1.ui.viewmodel.InsertTugasUiState
import com.example.uasa1.ui.viewmodel.InsertTugasViewModel
import com.example.uasa1.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiInsertTugas : DestinasiNavigasi {
    override val route = "insert_tugas"
    override val titleRes = "Insert Tugas"
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TugasInsertScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    @SuppressLint("NewApi")
    viewModel: InsertTugasViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertTugas.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        TugasEntryBody(
            tugasUiState = viewModel.insertTugasUiState,
            onTugasValueChange = viewModel::updateInsertTugasState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertTugas()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun TugasEntryBody(
    tugasUiState: InsertTugasUiState,
    onTugasValueChange: (InsertTugasEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        TugasFormInput(
            tugasInsertUiEvent = tugasUiState.insertTugasEvent,
            onValueChange = onTugasValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun TugasFormInput(
    tugasInsertUiEvent: InsertTugasEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertTugasEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Dropdown(
            selectedValue = tugasInsertUiEvent.id_proyek.toString(),
            onValueChangedEvent = { newValue ->
                println("Selected ID: $newValue") // Debug log
                onValueChange(tugasInsertUiEvent.copy(id_proyek = newValue.toIntOrNull() ?: 0))
            },
            options = DataProyek.DataProyekList().map { it.id_proyek.toString() },
            label = "ID Proyek",
        )

        Dropdown(
            selectedValue = tugasInsertUiEvent.id_tim.toString(),
            onValueChangedEvent = { newValue ->
                println("Selected ID: $newValue") // Debug log
                onValueChange(tugasInsertUiEvent.copy(id_tim = newValue.toIntOrNull() ?: 0))
            },
            options = DataTim.DataTimList().map { it.id_tim.toString() },
            label = "ID Tim",
        )

        OutlinedTextField(
            value = tugasInsertUiEvent.nama_tugas,
            onValueChange = { onValueChange(tugasInsertUiEvent.copy(nama_tugas = it)) },
            label = { Text("Nama Tugas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = tugasInsertUiEvent.deskripsi_tugas,
            onValueChange = { onValueChange(tugasInsertUiEvent.copy(deskripsi_tugas = it)) },
            label = { Text("Deskripsi Tugas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = false
        )

        Dropdown(
            selectedValue = tugasInsertUiEvent.prioritas,
            onValueChangedEvent = { newValue ->
                onValueChange(tugasInsertUiEvent.copy(prioritas = newValue))
            },
            options = listOf("Rendah", "Sedang", "Tinggi"),
            label = "Prioritas",
        )

        Dropdown(
            selectedValue = tugasInsertUiEvent.status_tugas,
            onValueChangedEvent = { newValue ->
                onValueChange(tugasInsertUiEvent.copy(status_tugas = newValue))
            },
            options = listOf("Belum Mulai", "Sedang Berlangsung", "Selesai"),
            label = "Status Tugas",
        )

        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}
