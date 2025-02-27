package com.hse.courseworkcompose.presentation.ui.profile

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.hse.courseworkcompose.R
import com.hse.courseworkcompose.domain.entity.Friend
import com.hse.courseworkcompose.domain.entity.Interest
import com.hse.courseworkcompose.domain.entity.User_.friends
import com.hse.courseworkcompose.presentation.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val user by viewModel.user.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val logoutFlag by viewModel.logoutFlag.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(logoutFlag) {
        if (logoutFlag == false) {
            Toast.makeText(context, "Выход выполнен", Toast.LENGTH_SHORT).show()
            navController.navigate("mainAuth")
        }
    }

    if (loading == true) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        user?.let { userData ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Box для выравнивания кнопки по правому краю
                Box(
                    modifier = Modifier.fillMaxWidth(), // Занимает всю ширину
                    contentAlignment = Alignment.CenterEnd // Выравниваем содержимое Box по правому краю
                ) {

                    // Кнопка выхода
                    Button(
                        onClick = { viewModel.logout() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black, // Цвет фона кнопки
                            contentColor = Color.Red   // Цвет содержимого (иконки или текста)
                        )
                    ) {
                        // Используем Icon для отображения иконки
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_logout_24), // Замените на ваш ресурс иконки
                            contentDescription = "Button Icon",
                            modifier = Modifier.size(24.dp) // Размер иконки
                        )
                    }
                }


                // Фото пользователя (Загружается с сервера)
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data("http://10.0.2.2:8080/user/avatar/${userData.globalId}/") // URL картинки
                        .crossfade(true)
//                        .error(R.drawable.default_avatar) // Если ошибка загрузки
//                        .placeholder(R.drawable.default_avatar) // Пока загружается
                        .build(),
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Имя и почта
                Text(
                    text = userData.userName,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = userData.email,
                    fontSize = 16.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Интересы
                SectionTitle("Интересы")

                Text(
                    text = userData.interest,
                    fontSize = 16.sp,
                    color = Color.Gray
                )

//                LazyColumn(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(150.dp)
//                ) {
//                    Text(userData.interest)
//                }

                Spacer(modifier = Modifier.height(16.dp))

                // Друзья
                SectionTitle("Друзья")
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                ) {

                    items(userData.friends) { friend ->
                        FriendItem(friend)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

            }
        } ?: run {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Ошибка загрузки профиля", color = Color.Red)
            }
        }
    }
}


@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}

//@Composable
//fun InterestItem(interestId: Int) {
//    val interest: Interest = Interest.fromValue(interestId)!!
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Image(
//            painter = painterResource(id = interest.imageId),
//            contentDescription = "Interest Icon",
//            modifier = Modifier.size(24.dp)
//        )
//        Spacer(modifier = Modifier.width(8.dp))
//        Text(text = interest.name, fontSize = 16.sp)
//    }
//}

@Composable
fun FriendItem(friend: Friend) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(

            model = ImageRequest.Builder(LocalContext.current)
                .data("http://10.0.2.2:8080/user/avatar/${friend.globalId}/") // URL картинки
                .crossfade(true)
//                        .error(R.drawable.default_avatar) // Если ошибка загрузки
//                        .placeholder(R.drawable.default_avatar) // Пока загружается
                .build(),
            contentDescription = "Friend Avatar",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = friend.userName, fontSize = 16.sp, fontWeight = FontWeight.Bold)
//            Text(text = friend., fontSize = 14.sp, color = Color.Gray)
        }
    }
}