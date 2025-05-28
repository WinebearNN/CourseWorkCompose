package com.hse.courseworkcompose.presentation.ui.selection

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hse.courseworkcompose.presentation.viewmodel.selection.SelectionViewModel


@Composable
fun PersonalSelectionScreen(
    navController: NavController,
    viewModel: SelectionViewModel = hiltViewModel()
) {


    val list = viewModel.list.collectAsState()
    val loading = viewModel.loading.collectAsState()

    val context = LocalContext.current

    val selectionCreatedFlow = navController.currentBackStackEntry?.savedStateHandle?.getStateFlow("selectionCreated", false)
    val selectionCreated = selectionCreatedFlow?.collectAsState() ?: remember { mutableStateOf(false) }

    LaunchedEffect(selectionCreated) {
        if (selectionCreated.value) {
            viewModel.loadData(context)
            navController.currentBackStackEntry?.savedStateHandle?.set("selectionCreated", false)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadData(context)
    }

    if (loading.value == false) {


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
                text = "Ваши подборки",
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
                        name = list.value!![number].name,
                        id = list.value!![number].globalId,
                        navController = navController
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xFFD9D9D9))
            )

            Row(
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = Modifier
                        .size(48.dp)
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(15.dp)
                        ),
                    onClick = {
                        navController.navigate(route = "addSelection"){
                            launchSingleTop=true
                        }
                    },

                    ) {
                    Icon(
                        modifier = Modifier
                            .size(48.dp),
                        imageVector = Icons.Outlined.Add,
                        contentDescription = "",
                        tint = Color(0xFF3D872A)
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xFFD9D9D9))
            )


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
    name: String,
    id: Long,
    navController: NavController
) {


//        IconButton(
//            modifier = Modifier
//                .padding(start = 16.dp)
//                .border(
//                    width = 1.dp,
//                    color = Color.LightGray,
//                    shape = RoundedCornerShape(15.dp)
//                ),
//            onClick = {},
//        ) {
//            Icon(
//                imageVector = Icons.Outlined.Delete,
//                contentDescription = "",
//                tint = Color(0xFF761010)
//            )
//        }

    Button(
        modifier = Modifier
            .padding(bottom = 16.dp)
            .height(48.dp)
            .widthIn(max = 320.dp),
        onClick = {
            navController.navigate(route = "search/$id"){
                launchSingleTop=true
            }
        },
        border = BorderStroke(
            width = 1.dp,
            color = Color.LightGray,
        ),
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        )
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = name,
            color = Color.Black,
            textAlign = TextAlign.Center,
            letterSpacing = 0.5.sp,
            fontFamily = FontFamily.Default,
            fontSize = 17.sp,
            fontStyle = FontStyle.Normal
        )
    }
}


@Composable
@Preview(showBackground = true)
fun PreviewPersonalSelectionScreen(modifier: Modifier = Modifier) {
    PersonalSelectionScreen(navController = rememberNavController())
}