package com.hse.courseworkcompose.presentation.ui.order

import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hse.courseworkcompose.R
import com.hse.courseworkcompose.domain.entity.Order
import com.hse.courseworkcompose.presentation.viewmodel.order.OrderViewModel
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

@Composable
fun PlacingOrderScreen(
    viewModel: OrderViewModel = hiltViewModel(),
    advertisementId: Long,
    navController: NavController
) {




    val advertisement = viewModel.advertisement.collectAsState()
    val loadingAdvertisement = viewModel.loadingAdvertisement.collectAsState()

    val loyaltyCard = viewModel.loyaltyCard.collectAsState()
    val loadingCard = viewModel.loadingCard.collectAsState()

    val scrollState = rememberScrollState()

    val context = LocalContext.current


    var userGlobalId =viewModel.userGlobalId.collectAsState()
    var latitude =viewModel.latitude.collectAsState()
    var longitude =viewModel.longitude.collectAsState()
    var address =viewModel.address.collectAsState()





    LaunchedEffect(Unit) {
        viewModel.loadData(context)
    }

    if (loadingCard.value == false) {


        val imageProvider = ImageProvider.fromResource(context, R.drawable.point)

        val mapView = MapView(context)

        var point by remember(latitude, longitude) {
            mutableStateOf<Point>(
                Point(
                    latitude.value.toDouble(),
                    longitude.value.toDouble()
                )
            )
        }

        var isByCash by remember { mutableStateOf<Boolean>(false) }


        mapView.apply {
            layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            mapWindow.map.move(
                CameraPosition(
                    point,
                    /* zoom = */ 17.0f,
                    /* azimuth = */ 0.0f,
                    /* tilt = */ 0.0f
                )
            )
        }
        mapView.mapWindow.map.mapObjects.addPlacemark { pm ->
            pm.geometry = point
            pm.setIcon(imageProvider)
            pm.setIconStyle(IconStyle().setScale(0.4f))
        }

        mapView.isEnabled = false



        MapKitFactory.getInstance().onStart()



        LaunchedEffect(Unit) {
            viewModel.getAdvertisement(advertisementId)
        }

        if (loadingAdvertisement.value == false) {

            val order by remember(userGlobalId, address) {
                mutableStateOf<Order>(
                    Order(
                        idAdvertisement = advertisementId,
                        userGlobalId = userGlobalId.value.toLong(),
                        isByCash = isByCash,
                        address = address.value,
                        amount = (advertisement.value!!.price * (1 - advertisement.value!!.sellerDiscount) * (1-loyaltyCard.value!!.loyaltyLevel.saleAmount)).toInt()
                    )
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    modifier = Modifier
                        .background(color = Color.White)
                        .height(56.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(Alignment.CenterVertically),
                    text = "Оформление заказа",
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
                        .background(Color.White)
                        .verticalScroll(
                            state = scrollState,
                        ),
                ) {

                    Row(
                        modifier = Modifier
                            .clickable(
                                enabled = true,
                                onClick = {
                                    navController.navigate(route = "specifyAddress") {
                                        launchSingleTop = true
                                    }
                                }
                            )
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically


                    ) {

                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 10.dp)
                                .weight(1f),
                            verticalArrangement = Arrangement.Center
                        ) {

                            Text(
                                modifier = Modifier
                                    .padding(vertical = 3.dp),
                                text = "Адрес",
                                fontSize = 18.sp,
                                letterSpacing = 0.5.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = address.value,
                                fontSize = 16.sp,
                                letterSpacing = 0.5.sp,
                            )
                            Row {
                                Text(
                                    modifier = Modifier
                                        .padding(vertical = 3.dp),
                                    letterSpacing = 0.5.sp,
                                    text = "Доставим в течение 3-4 дней",
                                    fontSize = 16.sp,
                                )
                            }


                        }

                        Icon(
                            modifier = Modifier
                                .padding(horizontal = 10.dp)
                                .size(48.dp),
                            imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                            contentDescription = ""
                        )
                    }


                    AndroidView(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .height(200.dp)
                            .border(
                                width = 1.dp,
                                shape = RoundedCornerShape(10.dp),
                                color = Color.Gray
                            ),
                        factory = { context ->
                            mapView
                        },

                        )

                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 30.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(vertical = 6.dp),
                            text = "Способ оплаты",
                            fontSize = 18.sp,
                            letterSpacing = 0.5.sp,
                            fontWeight = FontWeight.Medium
                        )

                        Row {
                            Column(
                                modifier = Modifier
                                    .padding(
                                        top = 10.dp,
                                        end = 10.dp
                                    )
                                    .widthIn(min = 150.dp, max = 240.dp)
                                    .background(
                                        shape = RoundedCornerShape(10.dp),
                                        color = Color.White
                                    )
                                    .clickable(
                                        onClick = {
                                            isByCash=!isByCash
                                        }
                                    )
                                    .border(
                                        width = 2.dp,
                                        color = if (!isByCash)
                                            Color.Black
                                        else
                                            Color.LightGray,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .padding(5.dp),
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Text(
                                    text = "Банковской \nкартой",
                                    textAlign = TextAlign.Start,
                                    fontSize = 16.sp,
                                )
                                Icon(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .align(Alignment.End),
                                    imageVector = ImageVector.vectorResource(R.drawable.bank_card),
                                    contentDescription = ""
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .padding(
                                        vertical = 10.dp,
                                        horizontal = 5.dp
                                    )
                                    .widthIn(min = 150.dp, max = 240.dp)
                                    .background(
                                        shape = RoundedCornerShape(10.dp),
                                        color = Color.White
                                    )
                                    .clickable(
                                        onClick = {
                                            isByCash=!isByCash
                                        }
                                    )
                                    .border(
                                        width = 2.dp,
                                        color = if (isByCash)
                                            Color.Black
                                        else
                                            Color.LightGray,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .padding(5.dp),
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Text(
                                    text = "Наличные \nрубли",
                                    fontSize = 16.sp,
                                )
                                Icon(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .align(Alignment.End),
                                    imageVector = ImageVector.vectorResource(R.drawable.cash),
                                    contentDescription = ""
                                )
                            }
                        }
                    }

                    Column(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, bottom = 30.dp)
                    ) {


                        Text(
                            modifier = Modifier
                                .padding(vertical = 8.dp),
                            text = "Сумма заказа",
                            fontSize = 18.sp,
                            letterSpacing = 0.5.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Row(
                            modifier = Modifier.padding(vertical = 2.dp)
                        ) {

                            Text(
                                modifier = Modifier
                                    .weight(1f),
                                text = "Заказ",
                                fontSize = 16.sp,
                                letterSpacing = 0.5.sp,

                                )
                            Text(
                                text = NumberFormat.getNumberInstance(Locale.getDefault())
                                    .format(advertisement.value!!.price) + " ₽",
                                fontSize = 16.sp,
                                letterSpacing = 0.5.sp,

                                )
                        }
                        Row(
                            modifier = Modifier.padding(vertical = 2.dp)
                        ) {

                            Text(
                                modifier = Modifier
                                    .weight(1f),
                                text = "Скидка продавца",
                                fontSize = 16.sp,
                                letterSpacing = 0.5.sp,

                                )
                            Text(
                                text = "- " + NumberFormat.getNumberInstance(Locale.getDefault())
                                    .format((advertisement.value!!.price * advertisement.value!!.sellerDiscount).toInt()) + " ₽",
                                fontSize = 16.sp,
                                letterSpacing = 0.5.sp,
                                color = Color(0xFFEB3131),

                                )
                        }

                        Row(
                            modifier = Modifier.padding(vertical = 2.dp)
                        ) {

                            Text(
                                modifier = Modifier
                                    .weight(1f),
                                text = "Программа лояльности",
                                fontSize = 16.sp,
                                letterSpacing = 0.5.sp,


                                )
                            Text(
                                text = "- " + NumberFormat.getNumberInstance(Locale.getDefault())
                                    .format((advertisement.value!!.price*(1-advertisement.value!!.sellerDiscount)*loyaltyCard.value!!.loyaltyLevel.saleAmount).toInt()) + " ₽",
                                fontSize = 16.sp,
                                letterSpacing = 0.5.sp,
                                color = Color(0xFFEB3131),


                                )
                        }

                        Row(
                            modifier = Modifier.padding(vertical = 2.dp)
                        ) {

                            Text(
                                modifier = Modifier
                                    .weight(1f),
                                text = "Доставка",
                                fontSize = 16.sp,
                                letterSpacing = 0.5.sp,

                                )
                            Text(
                                text = NumberFormat.getNumberInstance(Locale.getDefault())
                                    .format(249) + " ₽",
                                fontSize = 16.sp,
                                letterSpacing = 0.5.sp,

                                )
                        }

                        Row(
                            modifier = Modifier.padding(vertical = 6.dp)
                        ) {

                            Text(
                                modifier = Modifier
                                    .weight(1f),
                                text = "Итого",
                                fontSize = 18.sp,
                                letterSpacing = 0.5.sp,

                                )
                            Text(
                                text = NumberFormat.getNumberInstance(Locale.getDefault())
                                    .format(order.amount.toInt()) + " ₽",
                                fontSize = 18.sp,
                                letterSpacing = 0.5.sp,

                                )
                        }

                    }
                    Button(
                        modifier = Modifier
                            .padding(horizontal = 10.dp, vertical = 15.dp)
                            .height(48.dp)
                            .fillMaxWidth(),
                        onClick = {
                                viewModel.placingOrder(order,navController)
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
                            text = "Оформить",
                            color = Color.White,
                            fontSize = 15.sp,
                            letterSpacing = 1.sp
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
//
//@Preview(showBackground = true)
//@Composable
//private fun PreviewPlacingOrder() {
//    PlacingOrderScreen("")
//}


