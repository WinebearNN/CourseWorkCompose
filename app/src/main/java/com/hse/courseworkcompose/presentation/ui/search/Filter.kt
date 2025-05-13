package com.hse.courseworkcompose.presentation.ui.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowOverflow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterScreen(
) {


    var productColor = remember { mutableStateListOf<String>() }
    var productPriceMin by remember { mutableStateOf("") }
    var productPriceMax by remember { mutableStateOf("") }
    var productMale = remember { mutableStateListOf<String>() }
    var productSize = remember { mutableStateListOf<String>() }


    val listOfColorFields = listOf(
        Pair(Color.Black, "черный"),
        Pair(Color.Gray, "серый"),
        Pair(Color(0xFF1A5506), "зеленый"),
        Pair(Color.White, "белый"),
        Pair(Color.Blue, "синий"),
    )

    val listOfMales = listOf(
        "Мужской",
        "Женский",
        "Унисекс"
    )

    val listOfSizes = listOf(
        Pair("XXS", "40"),
        Pair("XS", "42"),
        Pair("S", "44"),
        Pair("M", "46"),
        Pair("L", "48"),
        Pair("XL", "50"),
        Pair("XXL", "52"),
        Pair("3XL", "54")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Text(
            modifier = Modifier
                .background(color = Color.White)
                .height(50.dp)
                .fillMaxWidth()
                .wrapContentHeight(Alignment.CenterVertically),
            text = "Фильтры",
            textAlign = TextAlign.Center,
            letterSpacing = 0.5.sp,
            fontFamily = FontFamily.Default,
            fontSize = 20.sp,
            fontStyle = FontStyle.Normal
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFFD9D9D9))
        )

        Column(
            modifier = Modifier
                .weight(1f)
        ) {


            Text(
                modifier = Modifier
                    .padding(horizontal = 30.dp, vertical = 10.dp)
                    .fillMaxWidth(),
                text = "Цвет",
                textAlign = TextAlign.Start,
                fontSize = 17.sp,
                color = Color.Black,
                letterSpacing = 0.5.sp
            )
            FlowRow(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.Start,
                verticalArrangement = Arrangement.Top,
                maxItemsInEachRow = 4,
                maxLines = 2,
                overflow = FlowRowOverflow.Clip
            ) {
                listOfColorFields.forEach { (color, colorName) ->
                    ColorField(
                        color = color,
                        colorName = colorName,
                        isSelected = productColor.contains(colorName),
                        onClick = {
                            if (productColor.contains(colorName)) {
                                productColor.remove(colorName)
                            } else {
                                productColor.add(colorName)
                            }
                        }
                    )
                }
            }

            Spacer(
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 15.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xFFD9D9D9))
            )

            Text(
                modifier = Modifier
                    .padding(horizontal = 30.dp, vertical = 10.dp)
                    .fillMaxWidth(),
                text = "Цена, ₽",
                textAlign = TextAlign.Start,
                fontSize = 17.sp,
                color = Color.Black,
                letterSpacing = 0.5.sp
            )



            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 10.dp)
            ) {


                //          MinPrice
                TextField(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .weight(1f)
                        .height(55.dp)
                        .padding(0.dp),
                    value = productPriceMin,
                    onValueChange = { productPriceMin = it },
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 16.sp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    leadingIcon = { Text("от", color = Color.Gray, fontSize = 16.sp) },
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFD9D9D9),
                        unfocusedContainerColor = Color(0xFFD9D9D9),
                        focusedTextColor = Color.Black,
                        focusedPlaceholderColor = Color.DarkGray,
                        unfocusedPlaceholderColor = Color.DarkGray,
                        unfocusedTextColor = Color.DarkGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = MaterialTheme.colorScheme.primary
                    ),

                    )

                TextField(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .weight(1f)
                        .height(55.dp),
                    value = productPriceMax,
                    onValueChange = { newValue ->
                        productPriceMax = newValue.filter { it.isDigit() }
                    },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Black
                    ),
                    leadingIcon = { Text("до", color = Color.Gray, fontSize = 16.sp) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFD9D9D9),
                        unfocusedContainerColor = Color(0xFFD9D9D9),
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedPlaceholderColor = Color.DarkGray,
                        unfocusedPlaceholderColor = Color.DarkGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = MaterialTheme.colorScheme.primary
                    ),
                )


            }
            Spacer(
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 15.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xFFD9D9D9))
            )


            Text(
                modifier = Modifier
                    .padding(horizontal = 30.dp, vertical = 10.dp)
                    .fillMaxWidth(),
                text = "Пол",
                textAlign = TextAlign.Start,
                fontSize = 17.sp,
                color = Color.Black,
                letterSpacing = 0.5.sp
            )

            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 10.dp)
            ) {

                listOfMales.forEach { it ->
                    OutlinedButton(
                        modifier = Modifier
                            .padding(horizontal = 5.dp),

                        border = BorderStroke(
                            width = 1.dp,
                            color =
                                if (productMale.contains(it))
                                    Color.Gray
                                else Color.Transparent
                        ),
                        shape = RoundedCornerShape(10.dp),
                        onClick = {
                            if (productMale.contains(it)) {
                                productMale.remove(it)
                            } else {
                                productMale.add(it)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(

                            containerColor = Color.LightGray,
                            contentColor = Color.DarkGray

                        )
                    ) {

                        Text(text = it)
                    }
                }
            }

            Spacer(
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 15.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xFFD9D9D9))
            )

            Text(
                modifier = Modifier
                    .padding(horizontal = 30.dp, vertical = 10.dp)
                    .fillMaxWidth(),
                text = "Размер",
                textAlign = TextAlign.Start,
                fontSize = 17.sp,
                color = Color.Black,
                letterSpacing = 0.5.sp
            )

            LazyRow(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            ) {

                items(listOfSizes.size) {
                    listOfSizes.forEach {
                        Column(
                            modifier = Modifier
                                .padding(
                                    horizontal = 5.dp
                                )
                                .size(50.dp)
                                .background(
                                    shape = RoundedCornerShape(10.dp),
                                    color = Color.LightGray
                                )
                                .clickable(
                                    onClick = {
                                        if (productSize.contains(it.second))
                                            productSize.remove(it.second)
                                        else
                                            productSize.add(it.second)
                                    }
                                )
                                .border(
                                    width = 1.dp,
                                    color = if (productSize.contains(it.second))
                                        Color.Gray
                                    else
                                        Color.Transparent,
                                    shape = RoundedCornerShape(10.dp)
                                ),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = it.first
                            )
                            Text(
                                text = it.second
                            )
                        }
                    }
                }
            }


        }



            Button(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 15.dp)
                    .height(45.dp)
                    .fillMaxWidth(),
                onClick = {
                    //TODO сделать метод <применить> во viewModel
                },
                border = BorderStroke(
                    width = 1.dp,
                    color = Color.Gray
                ),
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = 2.dp,
                    pressedElevation = 1.dp,
                    focusedElevation = 2.dp,
                    hoveredElevation = 2.dp
                ),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                )
            ) {
                Text(
                    text = "Применить",
                    color = Color.White,
                    fontSize = 15.sp,
                    letterSpacing = 1.sp
                )
            }
        }





}


@Composable
private fun ColorField(
    color: Color,
    colorName: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 5.dp)
            .wrapContentSize()
            .background(
                color = Color.LightGray,
                shape = RoundedCornerShape(15.dp)
            )
            .border(
                width = 1.dp,
                color = if (isSelected) Color.Black else Color.Transparent,
                shape = RoundedCornerShape(15.dp)
            )
            .padding(horizontal = 2.dp, vertical = 4.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(
            Modifier
                .padding(horizontal = 2.dp)
                .size(17.dp)
        ) {
            drawCircle(color = color)
        }
        Text(
            modifier = Modifier
                .padding(horizontal = 2.dp),
            text = colorName,
            fontSize = 15.sp,
            letterSpacing = 0.5.sp
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewFilterModalNavigationDrawer() {
    FilterScreen()
}