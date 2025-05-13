package com.hse.courseworkcompose.presentation.ui.advertsiment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.hse.courseworkcompose.R
import java.text.NumberFormat
import java.util.Locale

@Composable
fun AdvertisementShortScreen(
    price: Int,
    isFavorite: Boolean,
    sellerDiscount: Float = 0f,
    url: String,
    brand: String,
    name: String
) {

    val isFavoriteFlag = remember { mutableStateOf(isFavorite) }
    Column {
        Box(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxSize()

        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(url)
                    .crossfade(true)
                    .build(),
                alignment = Alignment.TopCenter,
                error = painterResource(R.drawable.placeholder),
                placeholder = painterResource(R.drawable.placeholder),
                contentDescription = "Фото товара",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .height(275.dp)
                    .background(Color.Black),
            )


//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .align(Alignment.TopStart)
//            ) {

            if (sellerDiscount != 0f) {

                Text(
                    modifier = Modifier
//                            .align(Alignment.Top)
                        .background(
                            color = Color(0xFFEB3131),
                            shape = RectangleShape
                        )
                        .padding(2.dp),
                    fontSize = 12.sp,
                    text = "-${(sellerDiscount * 100).toInt()}%",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }


//            }
            IconToggleButton(
                modifier = Modifier
                    .padding(5.dp)
                    .align(Alignment.BottomEnd)
                    .size(30.dp),
                checked = isFavoriteFlag.value,
                onCheckedChange = { isFavoriteFlag.value = !isFavoriteFlag.value },
            ) {
                Image(
                    imageVector = if (isFavoriteFlag.value) ImageVector.vectorResource(R.drawable.like_filled) else ImageVector.vectorResource(
                        R.drawable.like
                    ),
                    contentDescription = "Favourite",
                    alignment = Alignment.BottomEnd,
                    modifier = Modifier
//                            .padding(top = 0.dp, end = 0.dp)
//                        .weight(1f)
                        .size(28.dp)

                )
            }


        }
        Column(
            modifier = Modifier
                .padding(top = 10.dp, bottom = 25.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 5.dp),
                horizontalArrangement = Arrangement.Start
            ) {

                if (sellerDiscount == 0f) {
                    Text(
                        text = NumberFormat.getNumberInstance(Locale.getDefault())
                            .format(price) + " ₽",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                    )
                } else {
                    Text(
                        text = NumberFormat.getNumberInstance(Locale.getDefault()).format(price),
                        textDecoration = TextDecoration.LineThrough,
                        fontSize = 15.sp,
                    )
                    Text(
                        text = NumberFormat.getNumberInstance(Locale.getDefault())
                            .format((price * (1 - sellerDiscount)).toInt()) + " ₽",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color(0xFFEB3131),
                        modifier = Modifier
                            .padding(start = 5.dp)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(top = 7.dp)
            ) {
                Text(
                    modifier = Modifier
                        .padding(
                            start = 5.dp,
                            end = 35.dp
                        ),
                    text = brand,
                    fontSize = 14.sp,
                    letterSpacing = 0.5.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

            }
            Row {
                Text(
                    modifier = Modifier
                        .padding(
                            start = 5.dp,
                            end = 35.dp
                        ),
                    text = name,
                    fontSize = 14.sp,
                    letterSpacing = 0.5.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,

                    )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAdvertisementShortScreen() {
    AdvertisementShortScreen(
        price = 15999,
        isFavorite = true,
        sellerDiscount = 0.1f,
        url = "URL_EXAMPLE",
        brand = "8 Horas of Silk",
        name = "Лонгслив"
    )
}
