package com.hse.courseworkcompose.presentation.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hse.courseworkcompose.presentation.ui.advertsiment.AdvertisementShortScreen
import com.hse.courseworkcompose.presentation.viewmodel.search.SearchViewModel


@Composable
fun SearchScreen(
    navController: NavController,
    selectionGlobalId: String,
    viewModel: SearchViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.getAdvertisementListBySelectionId(selectionGlobalId)
    }

    val list = viewModel.list.collectAsState()
    val loading = viewModel.loading.collectAsState()

    if (loading.value == false) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center
        ) {
            items(list.value!!.size) { number ->
                AdvertisementShortScreen(
                    navController = navController,
                    list.value!![number]
                )
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewSearchScreen() {
    SearchScreen(navController = rememberNavController(), "")
}