package com.hse.courseworkcompose.presentation.ui.logIn

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hse.courseworkcompose.R
import com.hse.courseworkcompose.presentation.ui.theme.Black
import com.hse.courseworkcompose.presentation.ui.theme.DarkGray
import com.hse.courseworkcompose.presentation.ui.theme.LightGray
import com.hse.courseworkcompose.presentation.ui.theme.TextGray
import com.hse.courseworkcompose.presentation.ui.theme.White
import com.hse.courseworkcompose.presentation.viewmodel.logIn.LogInResult
import com.hse.courseworkcompose.presentation.viewmodel.logIn.LoginViewModel
import com.hse.courseworkcompose.util.ErrorCode
import kotlinx.coroutines.launch

private const val TAG = "LogInScreen"

@Composable
fun LogInScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {


    val logInResult by viewModel.logInResult.collectAsState()
    val scope = rememberCoroutineScope()


    val context = LocalContext.current;
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var phoneError by remember { mutableStateOf<String?>(null) }



    LaunchedEffect(logInResult) {
        when (logInResult) {


            is LogInResult.Error -> {
                val error = (logInResult as LogInResult.Error).exception
                Toast.makeText(context, error.message, Toast.LENGTH_LONG).show()
                Log.e(TAG, "LogIn failed", error)
            }

            LogInResult.Loading -> {
            }

            is LogInResult.ValidationError -> {
                val errors = (logInResult as LogInResult.ValidationError).errorCodes
                errors.forEach { errorCode ->
                    when (errorCode.value) {
                        101 -> {
                            emailError = ErrorCode.ERROR_101.translation
                        }

                        102 -> {
                            passwordError = ErrorCode.ERROR_102.translation
                        }

                        103 -> {
                            phoneError = ErrorCode.ERROR_103.translation
                        }
                    }
                    Log.w(TAG, "Validation error: $errorCode")
                }
            }

            is LogInResult.UserSuccess -> {
                navController.navigate(route = "profile") {
                    popUpTo("logIn")
                    launchSingleTop = true
                }
            }


        }
    }


    var isLogin by remember { mutableStateOf(true) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_light),
                contentDescription = "Логотип",
                modifier = Modifier.size(96.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                TabButton(
                    text = "Войти",
                    isSelected = isLogin,
                    onClick = {
                        isLogin = true
                        emailError = null
                        passwordError = null
                        phoneError = null
                    },
                )
                Spacer(modifier = Modifier.width(16.dp))
                TabButton(
                    text = "Зарегистрироваться",
                    isSelected = !isLogin,
                    onClick = {
                        isLogin = false
                        emailError = null
                        passwordError = null
                        phoneError = null
                    },
                )
            }

            if (isLogin) {
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = null
                    },
                    isError = emailError != null,
                    supportingText = {
                        if (emailError != null) {
                            Text(text = emailError!!, color = Color.Red)
                        }
                    },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email,imeAction = ImeAction.Done),
                    colors = OutlinedTextFieldDefaults.colors(

                        focusedContainerColor = White,
                        unfocusedContainerColor = White,

                        focusedLabelColor = Black,
                        unfocusedLabelColor = TextGray,

                        focusedBorderColor = Black,
                        unfocusedBorderColor = Black,

                        cursorColor = Black
                    ),
                    leadingIcon = {
                        Icon(
                            painter = rememberVectorPainter(image = Icons.Default.Email),
                            contentDescription = "Email icon",
                            tint = Black
                        )
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = null
                    },
                    isError = passwordError != null,
                    supportingText = {
                        if (passwordError != null) {
                            Text(text = passwordError!!, color = Color.Red)
                        }
                    },
                    singleLine = true,
                    label = { Text("Пароль") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    colors = OutlinedTextFieldDefaults.colors(

                        focusedContainerColor = White,
                        unfocusedContainerColor = White,

                        focusedLabelColor = Black,
                        unfocusedLabelColor = TextGray,

                        focusedBorderColor = Black,
                        unfocusedBorderColor = Black,

                        cursorColor = Black
                    ),
                    leadingIcon = {
                        Icon(
                            painter = rememberVectorPainter(image = Icons.Default.Lock),
                            contentDescription = "Password icon",
                            tint = Black
                        )
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        scope.launch {
                            viewModel.logInUser(email = email, password = password)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0F2954),
                        contentColor = White
                    )
                ) {
                    Text("Войти")
                }
            } else {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Ваше имя") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(


                        focusedContainerColor = White,
                        unfocusedContainerColor = White,

                        focusedLabelColor = Black,
                        unfocusedLabelColor = TextGray,

                        focusedBorderColor = Black,
                        unfocusedBorderColor = Black,

                        cursorColor = Black
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    leadingIcon = {
                        Icon(
                            painter = rememberVectorPainter(image = Icons.Default.Person),
                            contentDescription = "Person icon",
                            tint = Black
                        )
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))


                OutlinedTextField(
                    value = surname,
                    onValueChange = { surname = it },
                    label = { Text("Ваша фамилия") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(


                        focusedContainerColor = White,
                        unfocusedContainerColor = White,

                        focusedLabelColor = Black,
                        unfocusedLabelColor = TextGray,

                        focusedBorderColor = Black,
                        unfocusedBorderColor = Black,

                        cursorColor = Black
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    leadingIcon = {
                        Icon(
                            painter = rememberVectorPainter(image = Icons.Default.Person),
                            contentDescription = "Person icon",
                            tint = Black
                        )
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = null
                    },
                    isError = passwordError != null,
                    supportingText = {
                        if (passwordError != null) {
                            Text(text = passwordError!!, color = Color.Red)
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Password
                    ),
                    label = { Text("Введите пароль") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(

                        focusedContainerColor = White,
                        unfocusedContainerColor = White,

                        focusedLabelColor = Black,
                        unfocusedLabelColor = TextGray,

                        focusedBorderColor = Black,
                        unfocusedBorderColor = Black,

                        cursorColor = Black
                    ),
                    leadingIcon = {
                        Icon(
                            painter = rememberVectorPainter(image = Icons.Default.Lock),
                            contentDescription = "Password icon",
                            tint = Black
                        )
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = phone,
                    onValueChange = {
                        phone = it
                        phoneError = null
                    },
                    isError = phoneError != null,
                    supportingText = {
                        if (phoneError != null) {
                            Text(text = phoneError!!, color = Color.Red)
                        }
                    },
                    singleLine = true,
                    label = { Text("Номер телефона") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Done
                    ),
                    colors = OutlinedTextFieldDefaults.colors(


                        focusedContainerColor = White,
                        unfocusedContainerColor = White,

                        focusedLabelColor = Black,
                        unfocusedLabelColor = TextGray,

                        focusedBorderColor = Black,
                        unfocusedBorderColor = Black,

                        cursorColor = Black
                    ),
                    leadingIcon = {
                        Icon(
                            painter = rememberVectorPainter(image = Icons.Default.Phone),
                            contentDescription = "Phone icon",
                            tint = Black
                        )
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = null
                    },
                    isError = emailError != null,
                    supportingText = {
                        if (emailError != null) {
                            Text(text = emailError!!, color = Color.Red)
                        }
                    },
                    label = { Text("Email") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = White,
                        unfocusedContainerColor = White,

                        focusedLabelColor = Black,
                        unfocusedLabelColor = TextGray,

                        focusedBorderColor = Black,
                        unfocusedBorderColor = Black,

                        cursorColor = Black
                    ),
                    leadingIcon = {
                        Icon(
                            painter = rememberVectorPainter(image = Icons.Default.Email),
                            contentDescription = "Email icon",
                            tint = Black
                        )
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = dob,
                    onValueChange = { newValue ->
                        val filteredValue = newValue.filter { it.isDigit() || it == '.' }
                        if (filteredValue.length <= 10) {
                            dob = filteredValue
                            val formattedText = formatDateInput(filteredValue)
                            dob = formattedText

                        }
                    },
                    isError = emailError != null,
                    supportingText = {
                        if (emailError != null) {
                            Text(text = emailError!!, color = Color.Red)
                        }
                    },
                    label = { Text("Дата рождения (дд.мм.гггг)") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = White,
                        unfocusedContainerColor = White,

                        focusedLabelColor = Black,
                        unfocusedLabelColor = TextGray,

                        focusedBorderColor = Black,
                        unfocusedBorderColor = Black,

                        cursorColor = Black
                    ),
                    leadingIcon = {
                        Icon(
                            painter = rememberVectorPainter(Icons.Default.Person),
                            contentDescription = "Иконка даты рождения",
                            tint = Color.Black
                        )
                    },
                )



                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        scope.launch {
                            viewModel.registerUser(
                                email = email,
                                password = password,
                                phoneNumber = phone,
                                name = name,
                                surname = surname,
                                dob = dob
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0F2954),
                        contentColor = White
                    )
                ) {
                    Text("Зарегистрироваться")
                }

            }
        }
    }
}

@Composable
private fun TabButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val backgroundColor = if (isSelected) {
        MaterialTheme.colorScheme.secondary
    } else {
        MaterialTheme.colorScheme.primary
    }

    val contentColor = if (isSelected) {
        MaterialTheme.colorScheme.onSecondary
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(8.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = if (isSelected) 4.dp else 0.dp
        ),
        border =


            if (!isSelected) {
                BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            } else {
                null
            }

    ) {
        Text(text)
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewLogInScreen() {
    LogInScreen(navController = rememberNavController())
}

private fun formatDateInput(input: String): String {
    val digits = input.filter { it.isDigit() }
    val builder = StringBuilder()
    for (i in digits.indices) {
        if (i == 2 || i == 4) builder.append('.')
        if (i < 8) builder.append(digits[i])
    }
    return builder.toString()
}

