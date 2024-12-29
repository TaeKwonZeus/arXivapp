package com.github.taekwonzeus.arxivapp.ui.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.taekwonzeus.arxivapp.ui.components.ArxivCard
import com.github.taekwonzeus.arxivapp.ui.models.MainModel
import com.github.taekwonzeus.arxivapp.ui.viewmodels.MainViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    navController: NavController
) {
    val state by viewModel.state.collectAsState()
    var search by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    var last by remember { mutableStateOf(state) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                search,
                modifier = Modifier
                    .fillMaxWidth(),
                onValueChange = {
                    search = it
                    if (search.contains("\n")) {
                        scope.launch {
                            search = search.replace("\n", "")
                            viewModel.state = MutableStateFlow(
                                MainModel(
                                    viewModel.apiService.queryRaw(search)
                                )
                            )
                        }
                    }
                    last = state
                    keyboardController?.hide()
                    focusManager.clearFocus(true)
                },
                placeholder = {
                    Row {
                        Spacer(Modifier.weight(1f))
                        Icon(Icons.Outlined.Search, "search icon")
                        Text("Search")
                        Spacer(Modifier.weight(1f))
                    }
                })
        }
        Box {
            LazyColumn(
            ) {
                val entries = state.entries
                when (entries.size) {
                    0 -> item { Text("Nothing here :c", fontWeight = FontWeight.Bold) }
                    else -> items(entries) { entry ->
                        ArxivCard(
                            entry,
                            viewModel,
                            navController
                        )
                    }
                }
                last = state

            }
        }
    }
}
