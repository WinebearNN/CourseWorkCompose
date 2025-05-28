package com.hse.courseworkcompose.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun MainNavScreen() {
    val navController = rememberNavController()
    val currentRoute = rememberNavRoute(navController)

    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar(currentRoute)) {
                BottomNavigationBar(navController)
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            AppNavGraph(
                navController = navController,
            )
        }
    }
}

@Composable
fun rememberNavRoute(navController: NavController): String? {
    var currentRoute by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            currentRoute = destination.route
        }
    }

    return currentRoute
}

private fun shouldShowBottomBar(route: String?): Boolean {
    val excludedRoutes = setOf(
        "profile",
        "home",
        "favorite",
        "selection",
        )
    return excludedRoutes.contains(route)
}