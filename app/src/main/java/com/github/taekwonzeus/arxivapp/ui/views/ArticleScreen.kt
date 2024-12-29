package com.github.taekwonzeus.arxivapp.ui.views

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.github.taekwonzeus.arxivapp.ui.viewmodels.MainViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ArticleScreen(modifier: Modifier = Modifier, viewModel: MainViewModel) {
    val article = viewModel.art
    if (article == null) {
        Text("wut")
        return
    }
    val intent = remember {
        Intent(
            Intent.ACTION_VIEW, Uri.parse(article.links[0].href)
        )
    }
    val context = LocalContext.current
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    context.startActivity(intent)
                },
            ) {
                Icon(Icons.Outlined.Search, "Download")
            }
        },
        content = {
            LazyColumn(
                modifier = Modifier.padding(16.dp)
            ) {
                item {
                    Text(article.title ?: "Untitled", style = MaterialTheme.typography.titleLarge)
                }
                item {
                    Spacer(Modifier.height(16.dp))
                    HorizontalDivider()
                    Spacer(Modifier.height(16.dp))
                    Row {
                        Spacer(Modifier.weight(1f))
                        Text(
                            "Author${if (article.authors.size > 1) "s" else ""}: ",
                            fontWeight = FontWeight.Bold
                        )
                        Text(article.authors.joinToString(separator = ", ") { it })
                    }
                    Spacer(Modifier.height(16.dp))
                }
                item {
                    Text(article.summary ?: "")
                }
            }
        })
}