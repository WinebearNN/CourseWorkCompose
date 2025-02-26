package com.hse.courseworkcompose.presentation.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

open class NavigationScreen(val route: String, val title: String, val icon: ImageVector) {
    object Profile : NavigationScreen("profile", "Профиль", Icons.Default.Person)
    object Chats : NavigationScreen("chats", "Чаты", Icons.Default.Email)
    object Settings : NavigationScreen("settings", "Настройки", Icons.Default.Settings)
}