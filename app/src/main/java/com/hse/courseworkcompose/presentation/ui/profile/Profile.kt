package com.hse.courseworkcompose.presentation.ui.profile

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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.hse.courseworkcompose.domain.entity.LoyaltyCard
import com.hse.courseworkcompose.domain.entity.User
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun ProfileScreen(
    navController: NavController,
) {

    val gradientColors = listOf(
        Color(0xE60F0F0F),
        Color(0xFF151515)
    )

//TODO connect ViewModel
    val loyaltyCard = LoyaltyCard()
    val user=User(
        name = "Владимир",
        surname = "Зимин",
        dateOfBirth = 1123012800000,
        phoneNumber = "89524705200"
    )


    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(0xFFF4F4F5)
            )
    ) {

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
                    imageVector = ImageVector.vectorResource(loyaltyCard.loyaltyLevel.drawableId),
                    contentDescription = "Карта лояльности",
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
                            text = "${user.name} ${user.surname}",
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
                            text = user.phoneNumber,
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
                            text = (SimpleDateFormat("dd/MM/yyyy")
                                    .format(Date(user.dateOfBirth))),
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
                            contentDescription = "qr код"
                        )


                        Text(
                            modifier = Modifier
                                .padding(
                                    end = 25.dp,
                                    bottom = 25.dp
                                )
                                .wrapContentSize(),
                            text = "${loyaltyCard.loyaltyLevel.level}: ${(loyaltyCard.loyaltyLevel.saleAmount*100).toInt()}%",
                            textAlign = TextAlign.Center,
                            color = Color(0xFF0F0F0F),
                            fontSize = 16.sp,
                        )

                    }
                }

            }

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
                ){}
                Chapter(
                    text = "Промокоды",
                    icon = ImageVector.vectorResource(R.drawable.promocode)
                ){}
                Chapter(
                    text = "Возвраты",
                    icon = ImageVector.vectorResource(R.drawable.returns)
                ){}
                Chapter(
                    text = "Сертификаты",
                    icon = ImageVector.vectorResource(R.drawable.certificates)
                ){}
                Chapter(
                    text = "Способы оплаты",
                    icon = ImageVector.vectorResource(R.drawable.card)
                ){}
                Chapter(
                    text = "Понравившееся",
                    icon = ImageVector.vectorResource(R.drawable.like)
                ){
                    navController.navigate("favourite") {
                        launchSingleTop = true
                    }
                }

                Chapter(
                    text = "Страна",
                    icon = ImageVector.vectorResource(R.drawable.country)
                ){}


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

                Chapter(
                    text = "Позвоните нам",
                    icon = ImageVector.vectorResource(R.drawable.support)
                ){}
                Chapter(
                    text = "Часто задаваемые вопросы",
                    icon = ImageVector.vectorResource(R.drawable.question)
                ){

                }
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
    icon: ImageVector,
    onClick: () -> Unit
) {
    Column(
        modifier= Modifier
            .clickable(
                onClick = onClick
            )
    ) {
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




@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    ProfileScreen(navController = rememberNavController())
}


