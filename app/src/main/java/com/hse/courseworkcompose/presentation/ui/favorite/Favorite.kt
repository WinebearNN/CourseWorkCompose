package com.hse.courseworkcompose.presentation.ui.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hse.courseworkcompose.presentation.ui.advertsiment.AdvertisementShortScreen
import com.hse.courseworkcompose.presentation.viewmodel.favorite.FavoriteViewModel


@Composable
fun FavouriteScreen(
    navController: NavController,
    viewModel: FavoriteViewModel = hiltViewModel()
) {

    val loading = viewModel.loading.collectAsState()
    val list = viewModel.list.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadData(context)
    }



    if (loading.value == false) {
        if (list.value?.isEmpty() == true) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        enabled = true,
                        onClick = {
                            viewModel.loadData(context)

                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Нет сохраненных объявлений, \nповторите попытку"
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center
            ) {
                list.value?.let { advertisements ->
                    items(advertisements.size) { index ->
                        AdvertisementShortScreen(
                            navController = navController,
                            advertisementShort = advertisements[index]
                        )
                    }
                }
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = Color.Blue
            )
        }
    }
}