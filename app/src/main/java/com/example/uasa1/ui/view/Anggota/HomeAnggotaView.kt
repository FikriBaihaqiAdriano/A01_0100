package com.example.uasa1.ui.view.Anggota

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
import com.example.uasa1.model.Anggota
import com.example.uasa1.ui.costumwidget.CostumeTopAppBar
import com.example.uasa1.ui.navigation.DestinasiNavigasi
import com.example.uasa1.ui.viewmodel.AnggotaTimUiState
import com.example.uasa1.ui.viewmodel.HomeAnggotaTimViewModel
import com.example.uasa1.ui.viewmodel.PenyediaViewModel

object DestinasiHomeAnggotaTim : DestinasiNavigasi {
    override val route = "home_anggota_tim"
    override val titleRes = "Home Anggota Tim"
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun HomeAnggotaTimScreen(
    onBackClick: () -> Unit,
    navigateToAnggotaTimEntry: () -> Unit,
    navigateToAnggotaUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    viewModel: HomeAnggotaTimViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeAnggotaTim.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = { viewModel.getAnggotaTim() },
                navigateUp = { onBackClick() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToAnggotaTimEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Anggota Tim")
            }
        },
    ) { innerPadding ->
        HomeAnggotaTimStatus(
            anggotaTimUiState = viewModel.anggotaTimUiState,
            retryAction = { viewModel.getAnggotaTim() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = { viewModel.deleteAnggotaTim(it.id_anggota) },
            onUpdateClick = { navigateToAnggotaUpdate(it.id_anggota) }
        )
    }
}

@Composable
fun HomeAnggotaTimStatus(
    anggotaTimUiState: AnggotaTimUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Anggota) -> Unit = {},
    onDetailClick: (Int) -> Unit = {},
    onUpdateClick: (Anggota) -> Unit = {}
) {
    when (anggotaTimUiState) {
        is AnggotaTimUiState.Loading -> OnLoadingAnggota(modifier = modifier.fillMaxSize())
        is AnggotaTimUiState.Success -> {
            if (anggotaTimUiState.anggotaTim.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Anggota Tim")
                }
            } else {
                AnggotaTimLayout(
                    anggotaTimList = anggotaTimUiState.anggotaTim,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it) },
                    onDeleteClick = { onDeleteClick(it) },
                    onUpdateClick = { onUpdateClick(it) }
                )
            }
        }
        is AnggotaTimUiState.Error -> OnErrorAnggota(retryAction, modifier = modifier.fillMaxSize())
        else -> {}
    }
}

@Composable
fun OnLoadingAnggota(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnErrorAnggota(retryAction: () -> Unit, modifier: Modifier = Modifier) {
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
fun AnggotaTimLayout(
    anggotaTimList: List<Anggota>,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit,
    onDeleteClick: (Anggota) -> Unit = {},
    onUpdateClick: (Anggota) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items = anggotaTimList, key = { it.id_anggota }) { anggotaTim ->
            AnggotaTimCard(
                anggotaTim = anggotaTim,
                modifier = Modifier.clickable { onDetailClick(anggotaTim.id_anggota) },
                onDeleteClick = { onDeleteClick(anggotaTim) },
                onUpdateClick = { onUpdateClick(anggotaTim) }
            )
        }
    }
}

@Composable
fun AnggotaTimCard(
    anggotaTim: Anggota,
    modifier: Modifier = Modifier,
    onDeleteClick: (Anggota) -> Unit = {},
    onUpdateClick: (Anggota) -> Unit = {}
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
                    text = anggotaTim.nama_anggota,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onUpdateClick(anggotaTim) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Update Anggota Tim"
                    )
                }
                IconButton(onClick = { onDeleteClick(anggotaTim) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Anggota Tim"
                    )
                }
            }
            Text(
                text = anggotaTim.peran,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
