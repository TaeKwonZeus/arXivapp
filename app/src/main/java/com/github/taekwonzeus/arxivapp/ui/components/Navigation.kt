package com.github.taekwonzeus.arxivapp.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.github.taekwonzeus.arxivapp.ui.viewmodels.MainViewModel
import com.github.taekwonzeus.arxivapp.ui.views.ArticleScreen
import com.github.taekwonzeus.arxivapp.ui.views.MainScreen

class Route(val name: String, val label: String, val icon: @Composable () -> Unit)

val routes = listOf(
    Route("main", "Main") { Icon(Icons.Default.Home, "Main") },
    Route("main", "Main") { Icon(Icons.Default.Home, "Main") }
)

@Composable
fun BottomNavbar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        routes.forEach { route ->
            NavigationBarItem(
                selected = currentRoute == route.name,
                icon = route.icon,
                label = { Text(route.label) },
                onClick = {
                    navController.navigate(route.name) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun NavigationHost(
    modifier: Modifier,
    navController: NavHostController,
    mainViewModel: MainViewModel

) {
    NavHost(navController, "main", modifier) {
        composable("main") {
            MainScreen(modifier.padding(0.dp), mainViewModel, navController)
        }
        composable("article") {
            ArticleScreen(modifier, mainViewModel)
        }
    }
}