package com.example.uasa1.ui.view.Tim

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uasa1.ui.costumwidget.CostumeTopAppBar
import com.example.uasa1.ui.navigation.DestinasiNavigasi
import com.example.uasa1.ui.viewmodel.PenyediaViewModel
import com.example.uasa1.ui.viewmodel.UpdateTimViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiUpdateTim : DestinasiNavigasi {
    override val route = "Update_Tim"
    override val titleRes = "Update Tim"
    const val ID_TIM = "id_tim"
    val routeWithArgs = "$route/{$ID_TIM}"
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTimScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
    viewModel: UpdateTimViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateTim.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ) { padding ->
        TimEntryBody(
            modifier = Modifier.padding(padding),
            timUiState = viewModel.TupdateUiState,
            onTimValueChange = viewModel::updateInsertTimState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateTim()
                    delay(600)
                    withContext(Dispatchers.Main) {
                        onNavigate()
                    }
                }
            }
        )
    }
}
