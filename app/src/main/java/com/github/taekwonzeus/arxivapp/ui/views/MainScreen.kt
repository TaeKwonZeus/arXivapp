package com.github.taekwonzeus.arxivapp.ui.views

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.github.taekwonzeus.arxivapp.ui.viewmodels.MainViewModel

@Composable
fun MainScreen(modifier: Modifier, viewModel: MainViewModel) {
    val state by viewModel.state.collectAsState()

    println(state.toString())
    Text(state.toString())
}