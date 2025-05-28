package com.hse.courseworkcompose.presentation.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hse.courseworkcompose.domain.entity.AdvertisementShort
import com.hse.courseworkcompose.presentation.ui.advertsiment.AdvertisementShortScreen
import com.hse.courseworkcompose.presentation.viewmodel.home.HomeViewModel

private const val URL_EXAMPLE: String = "https://example.com/image.jpg"

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    var list = viewModel.list.collectAsState()
    var loading = viewModel.loading.collectAsState()

    val context= LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getAdvertisementShortList(context)
    }



    if (loading.value == false) {
        Column {

//        Box{
//
//            Row(
//                modifier = Modifier
//                    .align (Alignment.TopStart)
//            ){
//
//                TextField(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
//                        .padding(0.dp)
//                        .align(Alignment.CenterVertically)
//                        .shadow(
//                            elevation = 4.dp,
//                            shape = RoundedCornerShape(25.dp),
//                            clip = true
//                        ),
//                    value = searchQuery,
//                    onValueChange = { newText -> searchQuery = newText },
//                    placeholder = {
//                        Text(
//                            "Поиск товара",
//                            modifier = Modifier.padding(bottom = 4.dp),
//                            style = MaterialTheme.typography.bodyMedium
//                        )
//                    },
//                    singleLine = true,
//                    shape = RoundedCornerShape(25.dp),
//                    colors = TextFieldDefaults.colors(
//                        focusedContainerColor = MaterialTheme.colorScheme.surface,
//                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
//                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
//                        unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
//                        focusedLabelColor = MaterialTheme.colorScheme.primary,
//                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
//                        focusedIndicatorColor = Color.Transparent,
//                        unfocusedIndicatorColor = Color.Transparent,
//                        cursorColor = MaterialTheme.colorScheme.primary
//                    ),
//                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences,
//                        autoCorrectEnabled = true,
//                        keyboardType = KeyboardType.Text,
//                        imeAction = ImeAction.Search,
//                    ),
//                    keyboardActions = KeyboardActions(
//                        onSearch = {
//                            // Действие при нажатии на поиск (например, фильтрация списка)
////                            viewModel.searchItems(searchQuery)
//                        }
//                    ),
//                    leadingIcon = {
//                        Icon(
//                            Icons.Default.Search,
//                            contentDescription = "Поиск",
//                            tint = MaterialTheme.colorScheme.onSurfaceVariant
//                        )
//                    },
//                    trailingIcon = {
//                        if (searchQuery.isNotEmpty()) {
//                            IconButton(
//                                onClick = { searchQuery = "" }
//                            ) {
//                                Icon(
//                                    Icons.Default.Close,
//                                    contentDescription = "Очистить",
//                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
//                                )
//                            }
//                        }
//                    }
//                )
//            }
//        }

            if (list.value!!.isNotEmpty()) {

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    items(list.value!!.size) { number ->
                        val advertisement = list.value!![number]

                        AdvertisementShortScreen(
                            navController = navController,
                            advertisementShort = AdvertisementShort(
                                globalId = advertisement.id,
                                price = advertisement.price,
                                isFavorite = advertisement.isFavorite,
                                sellerDiscount = advertisement.sellerDiscount,
                                url = advertisement.url.first(),
                                brand = advertisement.brand,
                                name = advertisement.name
                            )
                        )
                    }
                }

            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(
                            enabled = true,
                            onClick = {
                                viewModel.getAdvertisementShortList(context)
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Не удалось загрузить товары"
                    )
                }
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = Color.Blue
            )
        }
    }


}


@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(
        navController = rememberNavController()
    )
}