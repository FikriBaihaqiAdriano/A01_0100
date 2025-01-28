package com.example.uasa1.ui.view.Tugas

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uasa1.R
import com.example.uasa1.model.Tugas
import com.example.uasa1.ui.costumwidget.CostumeTopAppBar
import com.example.uasa1.ui.navigation.DestinasiNavigasi
import com.example.uasa1.ui.viewmodel.HomeTugasViewModel
import com.example.uasa1.ui.viewmodel.PenyediaViewModel
import com.example.uasa1.ui.viewmodel.TugasUiState

object DestinasiHomeTugas : DestinasiNavigasi {
    override val route = "home_tugas"
    override val titleRes = "Home Tugas"
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun HomeTugasScreen(
    onBackClick: () -> Unit,
    navigateToTugasEntry: () -> Unit,
    navigateToTugasUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    viewModel: HomeTugasViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeTugas.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = { viewModel.getTugas() },
                navigateUp = { onBackClick() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToTugasEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Tugas")
            }
        },
    ) { innerPadding ->
        HomeTugasStatus(
            tugasUiState = viewModel.tugasUiState,
            retryAction = { viewModel.getTugas() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onEditClick = { navigateToTugasUpdate(it.id_tugas) },
            onDeleteClick = { viewModel.deleteTugas(it.id_tugas) }
        )
    }
}

@Composable
fun HomeTugasStatus(
    tugasUiState: TugasUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Tugas) -> Unit = {},
    onDetailClick: (Int) -> Unit = {},
    onEditClick: (Tugas) -> Unit = {}
) {
    when (tugasUiState) {
        is TugasUiState.Loading -> OnLoadingTugas(modifier = modifier.fillMaxSize())
        is TugasUiState.Success -> {
            if (tugasUiState.tugas.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Tugas")
                }
            } else {
                TugasLayout(
                    tugasList = tugasUiState.tugas,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it) },
                    onEditClick = { onEditClick(it) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        is TugasUiState.Error -> OnErrorTugas(retryAction, modifier = modifier.fillMaxSize())
        else -> {}
    }
}

@Composable
fun OnLoadingTugas(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnErrorTugas(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun TugasLayout(
    tugasList: List<Tugas>,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit,
    onEditClick: (Tugas) -> Unit,
    onDeleteClick: (Tugas) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(tugasList) { tugas ->
            TugasCard(
                tugas = tugas,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(tugas.id_tugas) },
                onEditClick = { onEditClick(tugas) },
                onDeleteClick = { onDeleteClick(tugas) }
            )
        }
    }
}

@Composable
fun TugasCard(
    tugas: Tugas,
    modifier: Modifier = Modifier,
    onEditClick: (Tugas) -> Unit = {},
    onDeleteClick: (Tugas) -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = tugas.nama_tugas,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onEditClick(tugas) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Tugas"
                    )
                }
                IconButton(onClick = { onDeleteClick(tugas) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                }
            }
            Text(
                text = "Prioritas: ${tugas.prioritas}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Status: ${tugas.status_tugas}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
