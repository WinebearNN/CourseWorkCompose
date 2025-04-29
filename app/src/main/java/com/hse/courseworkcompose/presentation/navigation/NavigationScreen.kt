package com.hse.courseworkcompose.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector

open class NavigationScreen(val route: String, val title: String, val icon: ImageVector) {
    object Profile : NavigationScreen("profile", "Профиль", Icons.Outlined.Person)
    object Favourite : NavigationScreen("favourite", "Понравившееся", Icons.Outlined.Favorite)
    object Search : NavigationScreen("search", "Поиск", Icons.Outlined.Search)
    object Main : NavigationScreen("main", "Главная", Icons.Outlined.Home)
    object LogIn : NavigationScreen("logIn","Вход", Icons.Default.Person)
    object Authentication : NavigationScreen("authentication", "Аутентификация", Icons.Default.Warning)
}
