package com.hse.courseworkcompose.presentation.ui.home

import android.R.attr.letterSpacing
import android.graphics.Color.alpha
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.graphics.vector.ImageVector
import com.hse.courseworkcompose.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import java.util.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.hse.courseworkcompose.presentation.ui.logIn.LogInScreen
import io.objectbox.Box
import java.text.NumberFormat

private const val URL_EXAMPLE: String = "https://example.com/image.jpg"

@Composable
fun HomeScreen(
    navController: NavController
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center
    ){
        items (6){
            Advertisement(
                price = 15999,
                isFavorite = true,
                sellerDiscount = 0.1f,
                url = URL_EXAMPLE,
                brand = "8 Horas of Silk",
                name="Лонгслив"
            )
        }
    }


}

@Composable
fun Advertisement(
    price: Int,
    isFavorite: Boolean,
    sellerDiscount: Float=0f,
    url: String,
    brand:String,
    name:String
    ) {
    Column() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp)
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


            
            Row(modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart)
            ) {

                if (sellerDiscount!=0f) {

                    Text(
                        modifier = Modifier
                            .align(Alignment.Top)
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

                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.like),
                    contentDescription = "Favourite",
                    alignment = Alignment.CenterEnd,
                    modifier = Modifier
                        .padding(top = 5.dp, end = 5.dp)
                        .fillMaxWidth()
                        .size(28.dp)

                )

            }



        }
        Column(
            modifier = Modifier
                .padding(top=10.dp,bottom = 25.dp)
        ) {
            Row(horizontalArrangement = Arrangement.Start) {

                if (sellerDiscount==0f) {
                    Text(
                        text = NumberFormat.getNumberInstance(Locale.getDefault())
                            .format(price)+" ₽",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .padding(start = 5.dp)
                    )
                }else {
                    Text(
                        text = NumberFormat.getNumberInstance(Locale.getDefault()).format(price),
                        textDecoration = TextDecoration.LineThrough,
                        fontSize = 15.sp,
                        modifier = Modifier
                            .padding(start = 5.dp),
                    )
                    Text(
                        text = NumberFormat.getNumberInstance(Locale.getDefault())
                            .format((price * (1 - sellerDiscount)).toInt()) + " ₽",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(top=7.dp)
            ) {
                Text(
                    modifier = Modifier
                        .padding(
                            start = 5.dp,
                            end = 35.dp
                        ),
                    text = brand,
                    fontSize = 14.sp,
                    letterSpacing=0.5.sp,
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
                    letterSpacing=0.5.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,

                )
            }
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