package com.hse.courseworkcompose.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationScreen.Home,
        NavigationScreen.Favourite,
        NavigationScreen.Profile
    )
//    val colorScheme = MaterialTheme.colorScheme

    NavigationBar(
        modifier = Modifier
            .background(Color.White)
            .height(80.dp),
        containerColor = Color(0xFFF4F4F5)
    ) {
        val navBackStackEntry = navController.currentBackStackEntryAsState().value
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { screen ->
            val selected = currentRoute == screen.route
            val iconColor = if (selected) Color.Black else Color.Gray
            val textColor = if (selected) Color.Black else Color.Gray

            NavigationBarItem(
                icon = {
                    Icon(
                        screen.icon,
                        contentDescription = screen.title,
                        tint = iconColor
                    )
                },
                label = {
                    Text(
                        screen.title,
                        color = textColor
                    )
                },
                selected = false,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
//                colors = NavigationBarItemDefaults.colors(
//                    indicatorColor = Color.Transparent,
//                    selectedIconColor = colorScheme.primary,
//                    unselectedIconColor = colorScheme.onPrimary,
//                    selectedTextColor = colorScheme.primary,
//                    unselectedTextColor = colorScheme.onPrimary,
//                )
            )
        }
    }
}