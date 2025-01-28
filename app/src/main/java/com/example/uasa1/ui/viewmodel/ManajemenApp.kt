package com.example.uasa1.ui.viewmodel

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.example.uasa1.ui.navigation.PengelolaHalaman

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManajemenApp(
    modifier: Modifier = Modifier // Optional untuk memberikan nilai default
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        // topBar = { TopAppBar(scrollBehavior = scrollBehavior) }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            PengelolaHalaman() // Ganti dengan konten atau navigasi khusus untuk ManajemenApp jika diperlukan
        }
    }
}
