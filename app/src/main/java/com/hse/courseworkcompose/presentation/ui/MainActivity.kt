package com.hse.courseworkcompose.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hse.courseworkcompose.presentation.ui.authorization.LoginScreen
import com.hse.courseworkcompose.presentation.ui.authorization.MainAuthScreen
import com.hse.courseworkcompose.presentation.ui.authorization.RegistrationScreen
import com.hse.courseworkcompose.presentation.ui.navigation.MainScreen
import com.hse.courseworkcompose.presentation.ui.profile.ProfileScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}
//
//val navController = rememberNavController()
//NavHost(navController = navController, startDestination = "mainAuth") {
//    composable("mainAuth") {
//        MainAuthScreen(navController) // Использование MainAuth
//    }
//    composable("login") {
//        LoginScreen(navController)
//    }
//    composable("registration") {
//        RegistrationScreen(navController)
//    }
//    composable("profile") {
//        ProfileScreen(navController)
//    }
//}