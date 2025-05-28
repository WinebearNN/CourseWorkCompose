package com.hse.courseworkcompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hse.courseworkcompose.presentation.ui.advertsiment.AdvertisementScreen
import com.hse.courseworkcompose.presentation.ui.authentication.AuthenticationScreen
import com.hse.courseworkcompose.presentation.ui.favorite.FavouriteScreen
import com.hse.courseworkcompose.presentation.ui.home.HomeScreen
import com.hse.courseworkcompose.presentation.ui.logIn.LogInScreen
import com.hse.courseworkcompose.presentation.ui.order.OrderListScreen
import com.hse.courseworkcompose.presentation.ui.order.PlacingOrderScreen
import com.hse.courseworkcompose.presentation.ui.order.SpecifyAddressScreen
import com.hse.courseworkcompose.presentation.ui.profile.ProfileScreen
import com.hse.courseworkcompose.presentation.ui.search.SearchScreen
import com.hse.courseworkcompose.presentation.ui.selection.AddSelectionScreen
import com.hse.courseworkcompose.presentation.ui.selection.PersonalSelectionScreen


@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "authentication"
    ) {
        composable(NavigationScreen.Profile.route) {
            ProfileScreen(navController = navController)
        }

        composable(NavigationScreen.LogIn.route) {
            LogInScreen(navController)
        }

        composable(
            route = NavigationScreen.PlacingOrder.route,
        ) { backStackEntry ->
            val globalId = backStackEntry.arguments?.getString("globalId") ?: "0"
            PlacingOrderScreen(
                advertisementId=globalId.toLong(),
                navController = navController
            )
        }

        composable(
            NavigationScreen.SpecifyAddress.route
        ) {
            SpecifyAddressScreen(navController = navController)
        }

        composable(NavigationScreen.Favourite.route) {
            FavouriteScreen(navController = navController)
        }
        composable(NavigationScreen.Selection.route) {
            PersonalSelectionScreen(navController = navController)
        }

        composable(
            route = NavigationScreen.Advertisement.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            AdvertisementScreen(navController=navController,globalId = id)
        }

        composable(
            route = NavigationScreen.SearchScreen.route,
            arguments = listOf(navArgument("selectionGlobalId") { type = NavType.StringType })
        ) { backStackEntry ->
            val selectionGlobalId = backStackEntry.arguments?.getString("selectionGlobalId") ?: ""
            SearchScreen(navController=navController,selectionGlobalId = selectionGlobalId)
        }

        composable(NavigationScreen.AddSelection.route) {
            AddSelectionScreen(navController)
        }

        composable(NavigationScreen.OrderList.route) {
            OrderListScreen(navController)
        }

        composable(NavigationScreen.Home.route) {
            HomeScreen(navController)
        }
        composable(NavigationScreen.Authentication.route) {
            AuthenticationScreen(navController)
        }

    }
}