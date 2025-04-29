package com.hse.courseworkcompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hse.courseworkcompose.presentation.ui.authentication.AuthenticationScreen
import com.hse.courseworkcompose.presentation.ui.logIn.LogInScreen
import com.hse.courseworkcompose.presentation.ui.profile.ProfileScreenTemp


@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "authentication"
    ) {
        composable(NavigationScreen.Profile.route) {
            ProfileScreenTemp(navController)
        }
//        composable(NavigationScreen.Settings.route) {
//            Sett(navController)
//        }
//        composable(NavigationScreen.Ticket.route) {
//            TicketScreen(navController)
//        }
        composable(NavigationScreen.LogIn.route) {
            LogInScreen(navController)
        }

//        composable(NavigationScreen.Error.route) {
//            ErrorScreen(navController)
//        }
        composable(NavigationScreen.Authentication.route) {
            AuthenticationScreen(navController)
        }
//        composable(NavigationScreen.Chats.route) { ChatScreen() }
//        composable(NavigationScreen.Settings.route) { SettingsScreen() }
    }
}