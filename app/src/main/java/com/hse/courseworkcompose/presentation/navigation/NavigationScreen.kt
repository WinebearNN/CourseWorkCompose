package com.hse.courseworkcompose.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Star
import androidx.compose.ui.graphics.vector.ImageVector

open class NavigationScreen(val route: String, val title: String, val icon: ImageVector) {
    object Profile : NavigationScreen("profile", "Профиль", Icons.Outlined.Person)
    object Favourite : NavigationScreen("favorite", "Сохраненное", Icons.Outlined.FavoriteBorder)
    object SpecifyAddress :
        NavigationScreen("specifyAddress", "Адресс доставки", Icons.Outlined.Edit)

    object PlacingOrder :
        NavigationScreen("placingOrder/{globalId}", "Оформление заказа", Icons.Outlined.Edit)

    object Home : NavigationScreen("home", "Главная", Icons.Outlined.Home)
    object LogIn : NavigationScreen("logIn", "Вход", Icons.Default.Person)
    object Authentication :
        NavigationScreen("authentication", "Аутентификация", Icons.Default.Warning)

    object Selection : NavigationScreen("selection", "Подборки", Icons.Outlined.Star)
    object Advertisement :
        NavigationScreen("advertisement/{id}", "Объявление", Icons.Default.Warning)

    object SearchScreen :
        NavigationScreen("search/{selectionGlobalId}", "Побдорка", Icons.Default.MailOutline)

    object AddSelection : NavigationScreen("addSelection", "Создать подборку", Icons.Default.Add)
    object OrderList : NavigationScreen("orderList", "список заказов", Icons.Default.MailOutline)
}
