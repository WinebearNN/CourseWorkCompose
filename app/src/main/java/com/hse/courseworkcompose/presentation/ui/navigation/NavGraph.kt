package com.hse.courseworkcompose.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hse.courseworkcompose.presentation.ui.authorization.LoginScreen
import com.hse.courseworkcompose.presentation.ui.authorization.MainAuthScreen
import com.hse.courseworkcompose.presentation.ui.authorization.RegistrationScreen
import com.hse.courseworkcompose.presentation.ui.profile.ProfileScreen


@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "mainAuth"
    ) {
        composable(NavigationScreen.Profile.route) { ProfileScreen(navController) }
        composable("mainAuth") {
            MainAuthScreen(navController) // Использование MainAuth
        }
        composable("login") {
            LoginScreen(navController)
        }
        composable("registration") {
            RegistrationScreen(navController)
        }
//        composable(NavigationScreen.Chats.route) { ChatScreen() }
//        composable(NavigationScreen.Settings.route) { SettingsScreen() }
    }
}