package com.example.uasa1.ui.view.Proyek

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uasa1.R
import com.example.uasa1.model.Proyek
import com.example.uasa1.ui.costumwidget.CostumeTopAppBar
import com.example.uasa1.ui.navigation.DestinasiNavigasi
import com.example.uasa1.ui.viewmodel.HomeProyekViewModel
import com.example.uasa1.ui.viewmodel.PenyediaViewModel
import com.example.uasa1.ui.viewmodel.ProyekUiState

object DestinasiHomeProyek : DestinasiNavigasi {
    override val route = "home_proyek"
    override val titleRes = "Home Proyek"
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeProyekScreen(
    onBackClick: () -> Unit,
    navigateToProyekEntry: () -> Unit,
    navigateToProyekUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    viewModel: HomeProyekViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeProyek.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getProyek()
                },
                navigateUp = {
                    onBackClick()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToProyekEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Proyek")
            }
        },
    ) { innerPadding ->
        HomeProyekStatus(
            homeProyekUiState = viewModel.proyekUIState,
            retryAction = { viewModel.getProyek() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteProyek(it.id_proyek)
                viewModel.getProyek()
            },
            onUpdateClick = { navigateToProyekUpdate(it.id_proyek) }
        )
    }
}

@Composable
fun HomeProyekStatus(
    homeProyekUiState: ProyekUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Proyek) -> Unit = {},
    onDetailClick: (Int) -> Unit = {},
    onUpdateClick: (Proyek) -> Unit = {}
) {
    when (homeProyekUiState) {
        is ProyekUiState.Loading -> OnLoadingProyek(modifier = modifier.fillMaxSize())

        is ProyekUiState.Success ->
            if (homeProyekUiState.proyek.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Proyek")
                }
            } else {
                ProyekLayout(
                    proyekList = homeProyekUiState.proyek,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id_proyek) },
                    onDeleteClick = { onDeleteClick(it) },
                    onUpdateClick = { onUpdateClick(it) }
                )
            }
        is ProyekUiState.Error -> OnErrorProyek(retryAction, modifier = modifier.fillMaxSize())
        else -> {}
    }
}

@Composable
fun OnLoadingProyek(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnErrorProyek(retryAction: () -> Unit, modifier: Modifier = Modifier) {
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
fun ProyekLayout(
    proyekList: List<Proyek>,
    modifier: Modifier = Modifier,
    onDetailClick: (Proyek) -> Unit,
    onDeleteClick: (Proyek) -> Unit = {},
    onUpdateClick: (Proyek) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(proyekList) { proyek ->
            ProyekCard(
                proyek = proyek,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(proyek) },
                onDeleteClick = { onDeleteClick(proyek) },
                onUpdateClick = { onUpdateClick(proyek) }
            )
        }
    }
}

@Composable
fun ProyekCard(
    proyek: Proyek,
    modifier: Modifier = Modifier,
    onDeleteClick: (Proyek) -> Unit = {},
    onUpdateClick: (Proyek) -> Unit = {}
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
                    text = proyek.nama_proyek,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onUpdateClick(proyek) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Proyek"
                    )
                }
                IconButton(onClick = { onDeleteClick(proyek) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }
            }
            Text(
                text = proyek.deskripsi_proyek,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}