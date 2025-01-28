package com.example.uasa1.ui.view.Tim

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
import com.example.uasa1.model.Tim
import com.example.uasa1.ui.costumwidget.CostumeTopAppBar
import com.example.uasa1.ui.viewmodel.HomeTimViewModel
import com.example.uasa1.ui.viewmodel.PenyediaViewModel
import com.example.uasa1.ui.viewmodel.TimUiState

object DestinasiHomeTim {
    val route = "home_tim"
    val titleRes = "Home Tim"
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTimScreen(
    onBackClick: () -> Unit,
    navigateToTimEntry: () -> Unit,
    navigateToTimUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    viewModel: HomeTimViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeTim.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = { viewModel.getTim() },
                navigateUp = { onBackClick() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToTimEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Tim")
            }
        },
    ) { innerPadding ->
        HomeTimStatus(
            homeTimUiState = viewModel.timUIState,
            retryAction = { viewModel.getTim() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onEditClick = { navigateToTimUpdate(it.id_tim) },
            onDeleteClick = {
                viewModel.deletetim(it.id_tim)
                viewModel.getTim()
            }
        )
    }
}

@Composable
fun HomeTimStatus(
    homeTimUiState: TimUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Tim) -> Unit = {},
    onDetailClick: (Int) -> Unit = {},
    onEditClick: (Tim) -> Unit = {}
) {
    when (homeTimUiState) {
        is TimUiState.Loading -> OnLoadingTim(modifier = modifier.fillMaxSize())
        is TimUiState.Success -> if (homeTimUiState.tim.isEmpty()) {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Tidak ada data Tim")
            }
        } else {
            TimLayout(
                timList = homeTimUiState.tim,
                modifier = modifier.fillMaxWidth(),
                onDetailClick = { onDetailClick(it.id_tim) },
                onEditClick = { onEditClick(it) },
                onDeleteClick = { onDeleteClick(it) }
            )
        }
        is TimUiState.Error -> OnErrorTim(retryAction, modifier = modifier.fillMaxSize())
        else -> {}
    }
}

@Composable
fun OnLoadingTim(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnErrorTim(retryAction: () -> Unit, modifier: Modifier = Modifier) {
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
fun TimLayout(
    timList: List<Tim>,
    modifier: Modifier = Modifier,
    onDetailClick: (Tim) -> Unit,
    onEditClick: (Tim) -> Unit,
    onDeleteClick: (Tim) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(timList) { tim ->
            TimCard(
                tim = tim,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(tim) },
                onEditClick = { onEditClick(tim) },
                onDeleteClick = { onDeleteClick(tim) }
            )
        }
    }
}

@Composable
fun TimCard(
    tim: Tim,
    modifier: Modifier = Modifier,
    onEditClick: (Tim) -> Unit = {},
    onDeleteClick: (Tim) -> Unit = {}
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
                    text = tim.nama_tim,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onEditClick(tim) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Tim"
                    )
                }
                IconButton(onClick = { onDeleteClick(tim) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Tim"
                    )
                }
            }
            Text(
                text = tim.deskripsi_tim,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
