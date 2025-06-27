package com.ckatsidzira.sipshappen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import androidx.navigation.navArgument
import com.ckatsidzira.sipshappen.presentation.features.beers.ui.BeersScreen
import com.ckatsidzira.sipshappen.presentation.features.details.ui.DetailsScreen
import com.ckatsidzira.sipshappen.presentation.features.home.ui.HomeScreen
import com.ckatsidzira.sipshappen.presentation.navigation.ui.TopNavBar
import com.ckatsidzira.sipshappen.presentation.navigation.NavScreen
import com.ckatsidzira.sipshappen.presentation.navigation.NavScreen.DetailArgs.ID
import com.ckatsidzira.sipshappen.presentation.navigation.ui.BottomNavBar
import com.ckatsidzira.sipshappen.ui.theme.SipsHappenTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SipsHappenTheme {
                val navController = rememberNavController()
                var title by remember { mutableStateOf("") }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopNavBar(
                            title = title,
                            showBackButton = title == NavScreen.Details.route,
                            onBackClick = { navController.popBackStack() }
                        )
                    },
                    bottomBar = { BottomNavBar(navController = navController) }
                ) { innerPadding ->
                    val graph =
                        navController.createGraph(startDestination = NavScreen.Home.route) {
                            composable(route = NavScreen.Home.route) {
                                HomeScreen(navController = navController)
                                title = NavScreen.Home.route
                            }
                            composable(
                                route = NavScreen.Details.route + "/{$ID}",
                                arguments = listOf(
                                    navArgument(ID) {
                                        type = NavType.IntType
                                    }
                                )
                            ) {
                                DetailsScreen(navController = navController)
                                title = NavScreen.Details.route
                            }
                            composable(route = NavScreen.Beers.route) {
                                BeersScreen(navController = navController)
                                title = NavScreen.Beers.route
                            }
                        }

                    NavHost(
                        navController = navController,
                        graph = graph,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SipsHappenTheme {

    }
}