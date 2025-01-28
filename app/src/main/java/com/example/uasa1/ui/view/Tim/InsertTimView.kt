package com.example.uasa1.ui.view.Tim

import android.annotation.SuppressLint
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
import com.example.uasa1.ui.costumwidget.CostumeTopAppBar
import com.example.uasa1.ui.navigation.DestinasiNavigasi
import com.example.uasa1.ui.viewmodel.InsertTimEvent
import com.example.uasa1.ui.viewmodel.InsertTimUiState
import com.example.uasa1.ui.viewmodel.InsertTimViewModel
import com.example.uasa1.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiInsertTim : DestinasiNavigasi {
    override val route = "insert_tim"
    override val titleRes = "Insert Tim"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimInsertScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    @SuppressLint("NewApi")
    viewModel: InsertTimViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertTim.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        TimEntryBody(
            timUiState = viewModel.TInserUiState,
            onTimValueChange = viewModel::updateInsertTimState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertTim()
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
fun TimEntryBody(
    timUiState: InsertTimUiState,
    onTimValueChange: (InsertTimEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        TimFormInput(
            timInsertUiEvent = timUiState.insertTimEvent,
            onValueChange = onTimValueChange,
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
fun TimFormInput(
    timInsertUiEvent: InsertTimEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertTimEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = timInsertUiEvent.nama_tim,
            onValueChange = { onValueChange(timInsertUiEvent.copy(nama_tim = it)) },
            label = { Text("Nama Tim") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = timInsertUiEvent.deskripsi_tim,
            onValueChange = { onValueChange(timInsertUiEvent.copy(deskripsi_tim = it)) },
            label = { Text("Deskripsi Tim") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}
