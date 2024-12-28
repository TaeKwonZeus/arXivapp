package com.github.taekwonzeus.arxivapp.ui.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.taekwonzeus.arxivapp.ui.components.ArxivCard
import com.github.taekwonzeus.arxivapp.ui.viewmodels.MainViewModel

@Composable
fun MainScreen(modifier: Modifier = Modifier, viewModel: MainViewModel) {
    val state by viewModel.state.collectAsState()
    Column() {
        Box {
            Column {
                Row {
                    Text("5 random articles", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "More options"
                    )
                }
            }
        }
        Box {
            LazyColumn(
                modifier = modifier
                    .padding(10.dp)
                    .fillMaxSize()
            ) {
                items(state) { entry ->
                    ArxivCard(entry)
                }
            }
        }
    }
}