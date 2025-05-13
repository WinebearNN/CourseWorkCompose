package com.hse.courseworkcompose.presentation.ui.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hse.courseworkcompose.presentation.ui.advertsiment.AdvertisementShortScreen


private const val URL_EXAMPLE: String = "https://example.com/image.jpg"


@Composable
fun FavouriteScreen(
    navController: NavController
){

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center
    ) {
        items(8) {
            AdvertisementShortScreen(
                price = 15999,
                isFavorite = true,
                sellerDiscount = 0.1f,
                url = URL_EXAMPLE,
                brand = "8 Horas of Silk",
                name = "Лонгслив"
            )
        }
    }

}

@Composable
@Preview(showBackground = true)
fun PreviewFavoriteScreen(){
    FavouriteScreen(
        navController = rememberNavController()
    )
}