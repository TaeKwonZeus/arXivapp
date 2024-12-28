package com.github.taekwonzeus.arxivapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.github.taekwonzeus.arxivapp.ui.components.BottomNavbar
import com.github.taekwonzeus.arxivapp.ui.components.NavigationHost
import com.github.taekwonzeus.arxivapp.ui.theme.ArXivappTheme
import com.github.taekwonzeus.arxivapp.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val mainViewModel: MainViewModel by viewModels()

        setContent {
            val navController = rememberNavController()

            ArXivappTheme {
                Scaffold { innerPadding ->
                    NavigationHost(Modifier.padding(innerPadding), navController, mainViewModel)
                }
            }
        }
    }
}