package com.example.uasa1.ui.view.Proyek

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uasa1.ui.costumwidget.CostumeTopAppBar
import com.example.uasa1.ui.costumwidget.Dropdown
import com.example.uasa1.ui.navigation.DestinasiNavigasi
import com.example.uasa1.ui.viewmodel.InsertProyekEvent
import com.example.uasa1.ui.viewmodel.InsertProyekUiState
import com.example.uasa1.ui.viewmodel.InsertProyekViewModel
import com.example.uasa1.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiInsertProyek : DestinasiNavigasi {
    override val route = "insert_proyek"
    override val titleRes = "Insert Proyek"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProyekInsertScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    @SuppressLint("NewApi")
    viewModel: InsertProyekViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertProyek.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        ProyekEntryBody(
            proyekUiState = viewModel.insertProyekUiState,
            onProyekValueChange = viewModel::updateInsertProyekState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertProyek()
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

@Composable
fun ProyekEntryBody(
    proyekUiState: InsertProyekUiState,
    onProyekValueChange: (InsertProyekEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        ProyekFormInput(
            proyekInsertUiEvent = proyekUiState.insertProyekEvent,
            onValueChange = onProyekValueChange,
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

@Composable
fun ProyekFormInput(
    proyekInsertUiEvent: InsertProyekEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertProyekEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = proyekInsertUiEvent.nama_proyek,
            onValueChange = { onValueChange(proyekInsertUiEvent.copy(nama_proyek = it)) },
            label = { Text("Nama Proyek") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = proyekInsertUiEvent.deskripsi_proyek,
            onValueChange = { onValueChange(proyekInsertUiEvent.copy(deskripsi_proyek = it)) },
            label = { Text("Deskripsi Proyek") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = proyekInsertUiEvent.tanggal_mulai,
            onValueChange = { onValueChange(proyekInsertUiEvent.copy(tanggal_mulai = it)) },
            label = { Text("Tanggal Mulai") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = proyekInsertUiEvent.tanggal_berakhir,
            onValueChange = { onValueChange(proyekInsertUiEvent.copy(tanggal_berakhir = it)) },
            label = { Text("Tanggal Berakhir") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        // Dropdown untuk Status Proyek
        Dropdown(
            selectedValue = proyekInsertUiEvent.status_proyek,
            onValueChangedEvent = { newValue ->
                onValueChange(proyekInsertUiEvent.copy(status_proyek = newValue))
            },
            options = listOf("Aktif", "Dalam Progres", "Selesai"),
            label = "Status Proyek",
            modifier = Modifier.fillMaxWidth()
        )
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}
