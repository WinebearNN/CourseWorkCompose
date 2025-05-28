package com.hse.courseworkcompose.presentation.ui.selection

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hse.courseworkcompose.domain.entity.Selection
import com.hse.courseworkcompose.presentation.viewmodel.selection.AddSelectionViewModel


@Composable
fun AddSelectionScreen(
    navController: NavController,
    viewModel: AddSelectionViewModel = hiltViewModel()
) {

    val loading = viewModel.loading.collectAsState()
    val userId = viewModel.userGlobalId.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadData(context)
    }


    var nameSelection by remember { mutableStateOf("") }

    var descriptionSelection by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier
                .background(color = Color.White)
                .height(56.dp)
                .fillMaxWidth()
                .wrapContentHeight(Alignment.CenterVertically),
            text = "Новая подборка",
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

        Box(
            modifier = Modifier
                .weight(1f)
        ) {

            Column(
                modifier = Modifier
                    .widthIn(max = 480.dp)

            ) {

                Column(
                    modifier = Modifier
                        .scrollable(rememberScrollState(), Orientation.Vertical)
                        .weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        modifier = Modifier
                            .padding(start = 20.dp, top = 15.dp),
                        text = "Название",
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 0.5.sp,
                        fontSize = 20.sp
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 15.dp)
                            .fillMaxWidth(),
                        value = nameSelection,
                        onValueChange = { nameSelection = it },
                        placeholder = {
                            Text(
                                "Например, имя человека"
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp)
                    )

                    Text(
                        modifier = Modifier
                            .padding(start = 20.dp, top = 15.dp),
                        text = "Описание",
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 0.5.sp,
                        fontSize = 20.sp
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 20.dp)
                            .fillMaxWidth()
                            .scrollable(rememberScrollState(), Orientation.Vertical)
                            .height(400.dp),
                        value = descriptionSelection,
                        onValueChange = { descriptionSelection = it },
                        placeholder = {
                            Text(
                                "Это могут быть привычки, интересы или хобби человека..."
                            )
                        },
                        singleLine = false,
                        maxLines = 15,
                        shape = RoundedCornerShape(10.dp)
                    )


                }


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
                .widthIn(max = 480.dp),
            verticalAlignment = Alignment.CenterVertically,

            ) {

            Button(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier
                    .padding(start = 16.dp)
                    .background(Color.Transparent)
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .weight(1f),
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

            Button(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 15.dp)
                    .height(48.dp)
                    .weight(2f),
                onClick = {
                    viewModel.createSelection(
                        selection = Selection(
                            userGlobalId = userId.value.toLong(),
                            name = nameSelection,
                            description = descriptionSelection,
                        ),
                        navController = navController
                    )
                },
                border = BorderStroke(
                    width = 1.dp,
                    color = Color.Gray
                ),
                enabled = nameSelection.isNotBlank() && descriptionSelection.isNotBlank(),
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
                    text = "Создать",
                    color = Color.White,
                    fontSize = 15.sp,
                    letterSpacing = 1.sp
                )
            }

        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun PreviewAddSelectionScreen() {
//    AddSelectionScreen()
//}