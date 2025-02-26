package com.hse.courseworkcompose.presentation.ui.authorization


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hse.courseworkcompose.presentation.viewmodel.AuthViewModel

private const val TAG="MainAuthScreen"
@Composable
fun MainAuthScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val authResult by viewModel.authResult.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.authUser()
    }

    LaunchedEffect(authResult) {
        authResult?.onSuccess {
            Toast.makeText(context, "Welcome back, ${it.userName}!", Toast.LENGTH_SHORT).show()
            navController.navigate("profile")
        }?.onFailure {
            Log.e(TAG,"User will stay at mainAuthScreen")
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFA4C8F5)),

//            .background(
////                painter = painterResource(id = R.drawable.auth_background),
//
//                contentScale = ContentScale.Crop
//            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { navController.navigate("login") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(Color.Black)
            ) {
                Text(text = "Login", color = Color.White, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate("registration") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(Color.White)
            ) {
                Text(text = "Sign Up", color = Color.Black, fontSize = 16.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainAuthScreen() {
    MainAuthScreen(navController = rememberNavController())
}


//    // Наблюдение за состоянием ViewModel
//    val authResult by viewModel.authResult.collectAsState()
//
//    LaunchedEffect(authResult) {
//        authResult?.let {
//            it.onSuccess { user ->
//                Log.i("MainAuth", "Authentication successful: ${user.userName}")
//                navController.navigate("profile")
//            }.onFailure { exception ->
//                Log.e("MainAuth", exception.message.toString())
//            }
//        }
//    }
//
//    // Вызов метода ViewModel при создании Composable
//    LaunchedEffect(Unit) {
//        viewModel.authUser()
//    }
//}