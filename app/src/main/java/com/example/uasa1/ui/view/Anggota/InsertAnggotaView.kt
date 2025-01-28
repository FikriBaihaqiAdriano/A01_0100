package com.example.uasa1.ui.view.Anggota

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
import com.example.uasa1.DataTim
import com.example.uasa1.ui.costumwidget.CostumeTopAppBar
import com.example.uasa1.ui.costumwidget.Dropdown
import com.example.uasa1.ui.navigation.DestinasiNavigasi
import com.example.uasa1.ui.viewmodel.InsertAnggotaTimEvent
import com.example.uasa1.ui.viewmodel.InsertAnggotaTimUiState
import com.example.uasa1.ui.viewmodel.InsertAnggotaTimViewModel
import com.example.uasa1.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiInsertAnggotaTim : DestinasiNavigasi {
    override val route = "insert_anggota"
    override val titleRes = "Insert Anggota Tim"
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnggotaTimInsertScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    @SuppressLint("NewApi")
    viewModel: InsertAnggotaTimViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertAnggotaTim.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        AnggotaTimEntryBody(
            anggotaTimUiState = viewModel.insertAnggotaTimUiState,
            onAnggotaTimValueChange = viewModel::updateInsertAnggotaTimState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertAnggotaTim()
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
fun AnggotaTimEntryBody(
    anggotaTimUiState: InsertAnggotaTimUiState,
    onAnggotaTimValueChange: (InsertAnggotaTimEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        AnggotaTimFormInput(
            anggotaTimInsertUiEvent = anggotaTimUiState.insertAnggotaTimEvent,
            onValueChange = onAnggotaTimValueChange,
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
fun AnggotaTimFormInput(
    anggotaTimInsertUiEvent: InsertAnggotaTimEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertAnggotaTimEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = anggotaTimInsertUiEvent.nama_anggota,
            onValueChange = { onValueChange(anggotaTimInsertUiEvent.copy(nama_anggota = it)) },
            label = { Text("Nama Anggota") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        Dropdown(
            selectedValue = anggotaTimInsertUiEvent.id_tim.toString(),
            onValueChangedEvent = { newValue ->
                println("Selected ID: $newValue")
                onValueChange(InsertAnggotaTimEvent().copy(id_tim = newValue.toIntOrNull() ?: 0))
            },
            options = DataTim.DataTimList().map { it.id_tim.toString() },
            label = "ID Tim",
        )

        Dropdown(
            selectedValue = anggotaTimInsertUiEvent.peran,
            onValueChangedEvent = { newValue ->
                onValueChange(anggotaTimInsertUiEvent.copy(peran = newValue))
            },
            options = listOf("Pemimpin", "Anggota"),
            label = "Peran Anggota",
        )
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}
