package com.hse.courseworkcompose.presentation.ui.order

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hse.courseworkcompose.presentation.viewmodel.order.OrderViewModel

@Composable
fun OrderListScreen(
    navController: NavController,
    viewModel: OrderViewModel = hiltViewModel()
) {


    val list = viewModel.list.collectAsState()
    val loadingOrder = viewModel.loadingOrder.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getOrdersList(context)
    }


    if (loadingOrder.value == false) {


        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Text(
                modifier = Modifier
                    .background(color = Color.White)
                    .height(56.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(Alignment.CenterVertically),
                text = "Ваши заказы",
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

            LazyColumn(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .weight(1f)
                    .align(Alignment.CenterHorizontally),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(list.value!!.size) { number ->
                    Chapter(
                        amount = list.value!![number].amount,
                        id = list.value!![number].globalId,
                        address = list.value!![number].address
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xFFD9D9D9))
            )
            Box(
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {

                Button(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                        .background(Color.Transparent)
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(10.dp)
                        ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    )
                ) {
                    Text(
                        text = "Назад",
                        color = Color.Black,
                        fontSize = 15.sp,
                        letterSpacing = 1.sp
                    )

                }
            }




        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.Blue
            )
        }
    }
}

@Composable
private fun Chapter(
    id:Long,
    amount:Int,
    address:String
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .border(
                width = 1.dp,
                color = Color.DarkGray,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 4.dp, vertical = 6.dp)
    ) {

        Text(
            modifier = Modifier
                .padding(bottom = 2.dp),
            text = "# $id",
            fontSize = 17.sp,
            textAlign = TextAlign.Center,
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFFD9D9D9))
        )

        Text(
            modifier = Modifier
                .padding(bottom = 2.dp),
            text = "Adress",
            fontSize = 16.sp,
            textAlign = TextAlign.Left,
        )

        Text(
            modifier = Modifier
                .padding(bottom = 2.dp),
            text = address,
            fontSize = 15.sp,
            textAlign = TextAlign.Left,
        )

        Row {
            Text(
                modifier = Modifier
                    .padding(bottom = 2.dp)
                    .weight(1f),
                text = "Сумма заказа",
                fontSize = 15.sp,
                textAlign = TextAlign.Left,
            )
            Text(
                modifier = Modifier
                    .padding(bottom = 2.dp)
                    .weight(1f),
                text = "$amount ₽",
                fontSize = 15.sp,
                textAlign = TextAlign.Left,
            )
        }



    }



}