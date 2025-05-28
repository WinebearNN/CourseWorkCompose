package com.hse.courseworkcompose.presentation.ui.advertsiment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.hse.courseworkcompose.R
import com.hse.courseworkcompose.domain.entity.AdvertisementShort
import com.hse.courseworkcompose.presentation.viewmodel.advertisement.AdvertisementViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun AdvertisementShortScreen(
    navController: NavController,
    advertisementShort: AdvertisementShort,
    viewModel: AdvertisementViewModel = hiltViewModel()
) {


    val painter = rememberAsyncImagePainter("http://10.0.2.2:8080/user/get/photo/polnaa-rastazka-zensiny-bol-sih-razmerov.jpg")

    val isFavoriteFlag = remember { mutableStateOf(advertisementShort.isFavorite) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadData(context,advertisementShort.globalId)
    }

    Column {
        Box(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxSize()

        ) {
//            AsyncImage(
//                model = ImageRequest.Builder(LocalContext.current)
//                    .data("https://plus.unsplash.com/premium_photo-1746201329166-64cc2408ef02?q=80&w=3687&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D")
//                    .crossfade(true)
//                    .networkCachePolicy(CachePolicy.ENABLED)
//                    .diskCachePolicy(CachePolicy.DISABLED)
//                    .memoryCachePolicy(CachePolicy.ENABLED)
//                    .build(),
////                model = "https://img.freepik.com/free-photo/full-shot-plus-sized-woman-stretching_23-2150172315.jpg",
//                alignment = Alignment.TopCenter,
////                error = painterResource(R.drawable.placeholder),
////                placeholder = painterResource(R.drawable.placeholder),
//                contentDescription = "Фото товара",
//                contentScale = ContentScale.FillBounds,
//                modifier = Modifier
//                    .height(275.dp)
//                    .background(Color.Black)
//                    .clickable(
//                        enabled = true,
//                        onClick = {
//                            navController.navigate("advertisement/${advertisementShort.globalId}/${"56.327402"}/${"44.007066"}/") {
//                                launchSingleTop = true
//                            }
//                        }
//                    ),
//            )

            Image(
                modifier = Modifier
                    .height(275.dp)
                    .background(Color.White)
                    .clickable(
                        enabled = true,
                        onClick = {
                            navController.navigate("advertisement/${advertisementShort.globalId}") {
                                launchSingleTop = true
                            }
                        }
                    ),
                painter = painter,
                contentDescription = "example using async image painter"
            )




            if (advertisementShort.sellerDiscount != 0f) {

                Text(
                    modifier = Modifier
                        .background(
                            color = Color(0xFFEB3131),
                            shape = RectangleShape
                        )
                        .padding(2.dp),
                    fontSize = 12.sp,
                    text = "-${(advertisementShort.sellerDiscount * 100).toInt()}%",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }


            IconToggleButton(
                modifier = Modifier
                    .padding(5.dp)
                    .align(Alignment.BottomEnd)
                    .size(30.dp),
                checked = isFavoriteFlag.value,
                onCheckedChange = {
                    isFavoriteFlag.value = !isFavoriteFlag.value
                    if (isFavoriteFlag.value){
                        viewModel.saveFavoriteAdvertisementShort(advertisementShort)
                    }else{
                        viewModel.deleteFavoriteAdvertisementShort(advertisementShort)
                    }
                },
            ) {
                Image(
                    imageVector = if (isFavoriteFlag.value) ImageVector.vectorResource(R.drawable.like_filled) else ImageVector.vectorResource(
                        R.drawable.like
                    ),
                    contentDescription = "Favourite",
                    alignment = Alignment.BottomEnd,
                    modifier = Modifier
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

                if (advertisementShort.sellerDiscount == 0f) {
                    Text(
                        text = NumberFormat.getNumberInstance(Locale.getDefault())
                            .format(advertisementShort.price) + " ₽",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                    )
                } else {
                    Text(
                        text = NumberFormat.getNumberInstance(Locale.getDefault())
                            .format(advertisementShort.price),
                        textDecoration = TextDecoration.LineThrough,
                        fontSize = 15.sp,
                    )
                    Text(
                        text = NumberFormat.getNumberInstance(Locale.getDefault())
                            .format((advertisementShort.price * (1 - advertisementShort.sellerDiscount)).toInt()) + " ₽",
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
                    text = advertisementShort.brand,
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
                    text = advertisementShort.name,
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
        navController = rememberNavController(),
        AdvertisementShort(
            globalId = 0,
            price = 15999,
            isFavorite = true,
            sellerDiscount = 0.1f,
            url = "URL_EXAMPLE",
            brand = "8 Horas of Silk",
            name = "Лонгслив"
        )
    )
}
