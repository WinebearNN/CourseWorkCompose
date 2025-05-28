package com.hse.courseworkcompose.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.hse.courseworkcompose.presentation.navigation.MainNavScreen
import dagger.hilt.android.AndroidEntryPoint
import com.hse.courseworkcompose.presentation.ui.theme.HSEprojectTheme
import com.yandex.mapkit.MapKitFactory

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MapKitFactory.initialize(this)
            HSEprojectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    MainNavScreen()
                }
            }
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