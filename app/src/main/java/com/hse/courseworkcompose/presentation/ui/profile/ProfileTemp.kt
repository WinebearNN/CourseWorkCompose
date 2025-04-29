package com.hse.courseworkcompose.presentation.ui.profile

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hse.courseworkcompose.R

@Composable
fun ProfileScreenTemp(
    navController: NavController,
) {

    val gradientColors = listOf(
        Color(0xE60F0F0F),  // #0F0F0F с opacity 90%
        Color(0xFF151515)   // #151515
    )

    val context = LocalContext.current

//    val colorScheme = MaterialTheme.colorScheme
//    val isDarkTheme = isSystemInDarkTheme()

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(0xFFF4F4F5)
            )
    ) {
//        Image(
//            imageVector = ImageVector.vectorResource(R.drawable.rainbow_vortex________),
//            contentDescription = "Android",
//            modifier = Modifier
//                .fillMaxSize()
//        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Box(
                modifier = Modifier
                    .padding(
                        start = 20.dp,
                        end = 20.dp,
                        top = 20.dp
                    )
                    .height(250.dp)
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.card_premium),
                    contentDescription = "Красная звезда",
                    modifier = Modifier
                        .fillMaxHeight()
                )


                Row(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .wrapContentWidth()
                    ) {

                        Text(
                            modifier = Modifier
                                .padding(
                                    top = 25.dp,
                                    start = 25.dp,
                                )
                                .wrapContentWidth(),
                            color = Color.Black,
                            text = "Владимир Зимин",
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(
                                brush = Brush.verticalGradient(
                                    colors = gradientColors
                                )),
                                letterSpacing = (0.5).sp

                        )
                        Text(
                            modifier = Modifier
                                .padding(
                                    top = 16.dp,
                                    start = 25.dp,
                                    bottom = 25.dp
                                )
                                .wrapContentWidth(),
                            color = Color(0xF0F0F0F),
                            text = "89524705200",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            style = TextStyle(
                                brush = Brush.verticalGradient(
                                    colors = gradientColors
                                )),
                            letterSpacing = (-0.5).sp


                        )

                        Spacer(
                            modifier = Modifier
                                .weight(1f)
                        )


                        Text(
                            modifier = Modifier
                                .padding(
                                    start = 25.dp,
                                    bottom = 25.dp
                                )
                                .wrapContentSize(),
                            color = Color(0xFF0F0F0F),
                            text = "03/08/2005",
                            fontSize = 16.sp
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f),
                        horizontalAlignment = Alignment.End
                    ) {

                        Image(
                            modifier = Modifier
                                .padding(end=25.dp, bottom = 25.dp)
                                .size(100.dp)
                                .weight(1f),
                            alignment = Alignment.BottomEnd,
                            bitmap = ImageBitmap.imageResource(R.drawable.qr_code_test),
                            contentDescription = "Зимний лес"
                        )

//                        Spacer(
//                            modifier = Modifier
//                                .weight(1f)
//                        )

                        Text(
                            modifier = Modifier
                                .padding(
//                                top = 20.dp,
                                    end = 25.dp,
                                    bottom = 25.dp
                                )
                                .wrapContentSize(),
                            text = "Important: 20%",
                            textAlign = TextAlign.Center,
                            color = Color(0xFF0F0F0F),
                            fontSize = 16.sp,
//                            fontWeight = FontWeight.Bold
                        )

                    }
                }

            }
//            Spacer(
//                modifier = Modifier
//                    .padding(
//                        top=20.dp
//                    )
//                    .height(6.dp)
//                    .fillMaxWidth()
//                    .background(Color.LightGray)
//            )

            Column(
                modifier = Modifier
                    .padding(
                        top = 60.dp
                    )
                    .padding(
                        start = 20.dp,
                        end = 20.dp,
                    )

            ) {
                Chapter(
                    text = "Заказы",
                    icon = ImageVector.vectorResource(R.drawable.orders)
                )
                Chapter(
//                    modifier = Modifier
//                        .padding(5.dp),
                    text = "Промокоды",
                    icon = ImageVector.vectorResource(R.drawable.promocode)
                )
                Chapter(
//                    modifier = Modifier
//                        .padding(5.dp),
                    text = "Возвраты",
                    icon = ImageVector.vectorResource(R.drawable.returns)
                )
                Chapter(
//                    modifier = Modifier
//                        .padding(5.dp),
                    text = "Сертификаты",
                    icon = ImageVector.vectorResource(R.drawable.certificates)
                )
                Chapter(
//                    modifier = Modifier
//                        .padding(5.dp),
                    text = "Способы оплаты",
                    icon = ImageVector.vectorResource(R.drawable.card)
                )
                Chapter(
//                    modifier = Modifier
//                        .padding(5.dp),
                    text = "Понравившееся",
                    icon = ImageVector.vectorResource(R.drawable.like)
                )

                Chapter(
//                    modifier = Modifier
//                        .padding(5.dp),
                    text = "Страна",
                    icon = ImageVector.vectorResource(R.drawable.country)
                )


            }

            Spacer(
                modifier = Modifier
                    .height(40.dp)
            )

            Column(
                modifier = Modifier
                    .padding(
                    )
                    .padding(
                        start = 20.dp,
                        end = 20.dp,
                    )
            ) {
//                Text(
//                    modifier = Modifier
//                        .padding(),
//                    text = "Поддержка",
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Bold
//                )
                Chapter(
//                    modifier = Modifier
//                        .padding(5.dp),
                    text = "Позвоните нам",
                    icon = ImageVector.vectorResource(R.drawable.support)
                )
                Chapter(
//                    modifier = Modifier
//                        .padding(5.dp),
                    text = "Часто задаваемые вопросы",
                    icon = ImageVector.vectorResource(R.drawable.question)
                )
                Spacer(
                    modifier = Modifier
                        .height(20.dp)
                )
            }


        }
    }

}

@Composable
fun Chapter(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector
) {
    Column() {
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .background(Color.LightGray)
                .fillMaxWidth()
        )
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(60.dp)
                .clickable(
                    enabled = true,
                    onClick = {}
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = icon,
                contentDescription = text,
                modifier = modifier
//                    .padding(start = 16.dp)
//                    .clickable(true, onClick = {})
                    .size(24.dp)
            )
            Text(
                text = text,
                modifier = modifier
                    .padding(start = 16.dp)
                    .fillMaxWidth()
                    .height(24.dp),
                fontSize = 18.sp,
                textAlign = TextAlign.Start
            )
        }

    }
}


@Composable
fun GradientCircleBackground() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        // Основной фон
        drawRect(color = Color(0xFFFF9D00), size = size)

        // Параметры для кругов
        val center = Offset(size.width / 2, size.height / 2)
        val maxRadius = size.maxDimension * 1.5f
        val strokeWidth = size.maxDimension * 0.0139f // Примерно 66.7 для 1600x800

        // Цвета кругов (в порядке от внешнего к внутреннему)
        val colors = listOf(
            Color(0xFFFF9D00),
            Color(0xFFF27D00),
            Color(0xFFE55F00),
            Color(0xFFD84400),
            Color(0xFFCB2C00),
            Color(0xFFBF1600),
            Color(0xFFB20300),
            Color(0xFFA5000E),
            Color(0xFF98001C),
            Color(0xFF8B0027),
            Color(0xFF7E0030),
            Color(0xFF710037),
            Color(0xFF64003B),
            Color(0xFF58003C),
            Color(0xFF4B003A),
            Color(0xFF3E0037),
            Color(0xFF310030),
            Color(0xFF210024)
        )

        // Рисуем круги
        colors.forEachIndexed { index, color ->
            val radius = maxRadius - (index * 100f * (maxRadius / 1800f))
            drawCircle(
                color = color,
                radius = radius,
                center = center,
                style = Stroke(width = strokeWidth)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    ProfileScreenTemp(navController = rememberNavController())
}


