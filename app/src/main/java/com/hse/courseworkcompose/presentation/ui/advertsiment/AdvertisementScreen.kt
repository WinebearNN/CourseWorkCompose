package com.hse.courseworkcompose.presentation.ui.advertsiment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.hse.courseworkcompose.R
import com.hse.courseworkcompose.domain.entity.Advertisement
import com.hse.courseworkcompose.presentation.ui.utill.RatingStar
import com.hse.courseworkcompose.presentation.ui.utill.TypedBar
import java.text.NumberFormat
import java.util.Locale





private val arrayTypes = Array<String>(5) { "Красный" }

private val advertisement = Advertisement(
    globalId = 0L,
    price = 10000,
    isFavorite = true,
    sellerDiscount = 0.1f,
    url = listOf(""),
    brand = "Adidas",
    name = "Yezzy boost 350v2",
    description = "Сумка выполнена из полиэстера с металлическим декором по всей поверхности. Особенности: 1 отделение, магнитная застежка, внутри текстильная подкладка без дополнительных карманов, ручка-цепочка.",
    rate = 3.2f,
    quantityReviews = 3500
)

@Composable
fun AdvertisementScreen(
    advertisement: Advertisement
) {

    var isFavoriteFlag by remember { mutableStateOf<Boolean?>(advertisement.isFavorite) }
    val pagerState = rememberPagerState(pageCount = {
        10
    })

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        item {

            Box(modifier = Modifier.fillMaxSize()) {


                Column {
                    Box(
                        modifier = Modifier.wrapContentSize()
                    ) {

                        HorizontalPager(
//                modifier = Modifier
//                    .background(Color.Black),
                            state = pagerState,
                            pageSpacing = 0.dp,
                            pageSize = PageSize.Fixed(300.dp),
                            contentPadding = PaddingValues(horizontal = 0.dp) // Убираем внутренние отступы
                        ) { page ->

                            Box(
                                modifier = Modifier
                                    .width(300.dp)
                                    .height(400.dp)
                                    .padding(0.dp) // Полное отсутствие отступов
//                        .clip(RoundedCornerShape(12.dp))
//                        .graphicsLayer {
//                            // Параллакс-эффект (опционально)
//                            val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
//                            alpha = lerp(0.5f, 1f, 1 - pageOffset.absoluteValue)
//                        }
                            ) {

                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(advertisement.url)
                                        .crossfade(true)
                                        .build(),
                                    alignment = Alignment.TopCenter,
                                    error = painterResource(R.drawable.placeholder_2),
                                    placeholder = painterResource(R.drawable.placeholder),
                                    contentDescription = "Фото товара",
                                    contentScale = ContentScale.FillBounds,
                                    modifier = Modifier
                                        .height(400.dp)
                                        .width(300.dp)
                                        .background(Color.White),
                                )
                            }

                        }
                        if (advertisement.sellerDiscount != 0f) {
                            Text(
                                modifier = Modifier
//                            .padding(bottom = 1.dp)
                                    .background(
                                        color = Color(0xFFEB3131),
                                        shape = RectangleShape
                                    )
                                    .align(Alignment.BottomStart)
                                    .padding(4.dp),
                                fontSize = 14.sp,
                                text = "-${(advertisement.sellerDiscount * 100).toInt()}%",
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                    Spacer(
                        modifier = Modifier
                            .height(1.dp)
                            .fillMaxWidth()
                            .background(Color(0xFFD9D9D9))
                    )



                    Row(
//                verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .fillMaxWidth()
                    ) {
                        Column {

                            Text(
                                modifier = Modifier
                                    .padding(top = 20.dp, start = 20.dp),
                                text = advertisement.brand,
                                fontWeight = FontWeight.Medium,
                                fontSize = 19.sp,
                                letterSpacing = 1.sp
                            )



                            Text(
                                modifier = Modifier
                                    .padding(top = 5.dp, start = 20.dp),
                                text = advertisement.name,
                                fontSize = 16.sp,
                                letterSpacing = 0.5.sp
                            )

                            Row(
                                modifier = Modifier
                                    .padding(start = 20.dp, top = 20.dp),
                                horizontalArrangement = Arrangement.Start
                            ) {

                                if (advertisement.sellerDiscount == 0f) {
                                    Text(
                                        text = NumberFormat.getNumberInstance(Locale.getDefault())
                                            .format(advertisement.price) + " ₽",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Medium,
                                    )
                                } else {
                                    Text(
                                        text = NumberFormat.getNumberInstance(Locale.getDefault())
                                            .format(advertisement.price),
                                        textDecoration = TextDecoration.LineThrough,
                                        fontSize = 17.sp,
                                    )
                                    Text(
                                        modifier = Modifier
                                            .padding(start = 10.dp),
                                        text = NumberFormat.getNumberInstance(Locale.getDefault())
                                            .format((advertisement.price * (1 - advertisement.sellerDiscount)).toInt()) + " ₽",
                                        color = Color(0xFFEB3131),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 17.sp,
                                    )
                                }
                            }
                        }
                        Column(
                            modifier = Modifier
                                .weight(1f),
//                        .padding(end = 20.dp),
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.Center
//                    contentAlignment = Alignment.BottomEnd

                        ) {
                            Column(
                                modifier = Modifier.padding(top = 20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {


//                        AsyncImage(
//                            modifier = Modifier
////                            .padding(end=20.dp)
//                                .height(48.dp)
//                                .width(48.dp)
////                                .align(Alignment.Center)
//                                .background(Color.White, shape = CircleShape)
//                                .border(1.dp, Color(0xFFD9D9D9), shape = CircleShape)
//                                .padding(10.dp),
//                            model = ImageRequest.Builder(LocalContext.current)
//                                .data(url)
//                                .crossfade(true)
//                                .build(),
//                            alignment = Alignment.Center,
//                            error = painterResource(R.drawable.placeholder_3),
//                            placeholder = painterResource(R.drawable.placeholder_3),
//                            contentDescription = "лого бренда",
//                            contentScale = ContentScale.FillBounds,
//                        )


                                RatingStar(
                                    modifier = Modifier
                                        .padding(top = 5.dp, start = 20.dp),
                                    value = 3.5f,
                                    starSize = 16.dp,
                                    fontSize = 14.sp,
                                    paddingStarValue = 5.dp,
                                    reviewQuantity = advertisement.quantityReviews
                                )
                            }
                        }
                    }

                    TypedBar(arrayTypes)

                    Button(
                        onClick = {},
                        modifier = Modifier
                            .padding(
                                top = 20.dp,
                                bottom = 40.dp,
                                end = 10.dp,
                                start = 10.dp
                            )
                            .fillMaxWidth()
                            .height(50.dp),

                        colors = ButtonColors(

                            containerColor = Color.Black,
                            disabledContentColor = Color.Black,

                            contentColor = Color.White,
                            disabledContainerColor = Color.White

                        ),
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Text(
                            text = "Добавить в корзину"
                        )
                    }


                    Spacer(
                        modifier = Modifier
//                    .padding(top = 30.dp)
                            .height(1.dp)
                            .fillMaxWidth()
                            .background(Color(0xFFD9D9D9))
                    )

                    Text(
                        modifier = Modifier
                            .padding(
                                top = 30.dp,
                                start = 20.dp
                            ),
                        text = "О товаре",
                        fontWeight = FontWeight.Medium,
                        fontSize = 22.sp

                    )

                    Text(
                        modifier = Modifier
                            .padding(
                                vertical = 15.dp,
                                horizontal = 20.dp
                            ),
                        text = advertisement.description,
                    )
                }


                Row {

                    Button(
                        modifier = Modifier
                            .padding(top = 5.dp, start = 15.dp)
                            .weight(1f),
                        contentPadding = PaddingValues(0.dp),
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        )
                    ) {

                        Image(
                            imageVector = ImageVector.vectorResource(R.drawable.arrow_back),
                            contentDescription = "Favourite",
                            alignment = Alignment.CenterStart,
                            modifier = Modifier
//                                .padding(top = 10.dp, start = 10.dp)
                                .weight(1f)
                                .size(36.dp)

                        )
                    }

                    Button(
                        modifier = Modifier
                            .padding(top = 5.dp, end = 15.dp)
                            .weight(1f),
                        contentPadding = PaddingValues(0.dp),
                        onClick = { isFavoriteFlag = !isFavoriteFlag!! },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        )
                    ) {


                        Image(
                            imageVector = if (isFavoriteFlag == true) ImageVector.vectorResource(R.drawable.like_filled) else ImageVector.vectorResource(
                                R.drawable.like
                            ),
                            contentDescription = "Favourite",
                            alignment = Alignment.CenterEnd,
                            modifier = Modifier
                                .size(36.dp)
                                .weight(1f)
                        )
                    }

                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun PreviewAdvertisement() {
    AdvertisementScreen(
        advertisement = advertisement
    )
}