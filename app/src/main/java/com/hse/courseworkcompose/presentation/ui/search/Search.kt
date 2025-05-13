package com.hse.courseworkcompose.presentation.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hse.courseworkcompose.R
import com.hse.courseworkcompose.presentation.ui.advertsiment.AdvertisementShortScreen


@Composable
fun SearchScreen(
    navController: NavController
){

    var searchQuery by remember { mutableStateOf("") } // Переименовал для ясности


    Column(modifier = Modifier
        .fillMaxSize()) {


        Row(
            modifier = Modifier
//                .background(Color.Gray)
                .padding(vertical = 20.dp)
//            verticalAlignment = Alignment.CenterVertically,

        ) {

            IconButton(
                onClick = {},
                modifier = Modifier
                    .padding(start = 15.dp)
                    .size(30.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.arrow_back),
                    contentDescription = "go back",
                    alignment = Alignment.BottomEnd,
                    modifier = Modifier
//                            .padding(top = 0.dp, end = 0.dp)
//                        .weight(1f)
                        .size(28.dp)

                )
            }

            TextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp)
                    .padding(0.dp)
                    .align(Alignment.CenterVertically)
                    .shadow( // Добавим тень для глубины
                        elevation = 4.dp,
                        shape = RoundedCornerShape(15.dp),
                        clip = true
                    ),
                value = searchQuery,
                onValueChange = { newText -> searchQuery = newText },
                placeholder = {
                    Text(
                        "Поиск товара",
                        modifier = Modifier.padding(bottom = 4.dp),  // Уменьшаем отступ снизу
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(15.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    autoCorrect = true,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
//                            viewModel.searchItems(searchQuery)
                    }
                ),
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Поиск",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(
                            onClick = { searchQuery = "" }
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Очистить",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            )

            IconButton(
                onClick = {},
                modifier = Modifier
                    .padding(end = 15.dp)
                    .size(30.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.filter),
                    contentDescription = "go back",
                    alignment = Alignment.BottomEnd,
                    modifier = Modifier
//                            .padding(top = 0.dp, end = 0.dp)
//                        .weight(1f)
                        .size(28.dp)

                )
            }
        }

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
                    url = "URL_EXAMPLE",
                    brand = "8 Horas of Silk",
                    name = "Лонгслив"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchScreen(){
    SearchScreen(navController = rememberNavController())
}