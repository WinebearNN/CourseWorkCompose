package com.hse.courseworkcompose.presentation.ui.logIn

import android.util.Log
import android.widget.Toast
import com.hse.courseworkcompose.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
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


    // 2. Обрабатываем результат
    LaunchedEffect(logInResult) {
        when (logInResult) {


            is LogInResult.Error -> {
                val error = (logInResult as LogInResult.Error).exception
                Toast.makeText(context, error.message, Toast.LENGTH_LONG).show()
                Log.e(TAG, "LogIn failed", error)
            }

            LogInResult.Loading -> {
                // Показываем загрузку (опционально)
            }

            is LogInResult.ValidationError -> {
                // Показываем ошибки валидации (если используете login-форму)
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


    // Получаем текущую цветовую схему
    val colorScheme = MaterialTheme.colorScheme
    val isDarkTheme = isSystemInDarkTheme()


    var isStudent by remember { mutableStateOf(true) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Логотип - можно добавить разные варианты для темной и светлой темы
            Image(
                painter = painterResource(id = if (isDarkTheme) R.drawable.logo_light else R.drawable.logo_light),
                contentDescription = "Логотип ВШЭ",
                modifier = Modifier.size(96.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Переключатель студент/гость
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                TabButton(
                    text = "Войти",
                    isSelected = isStudent,
                    onClick = {
                        isStudent = true
                        emailError = null
                        passwordError = null
                        phoneError = null
                    },
                    isDarkTheme = isDarkTheme
                )
                Spacer(modifier = Modifier.width(16.dp))
                TabButton(
                    text = "Зарегистрироваться",
                    isSelected = !isStudent,
                    onClick = {
                        isStudent = false
                        emailError = null
                        passwordError = null
                        phoneError = null
                    },
                    isDarkTheme = isDarkTheme
                )
            }

            if (isStudent) {
                // Форма для студентов
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = null
                    },
                    isError = emailError != null,
                    supportingText = {
                        if (emailError != null) {
                            Text(text = emailError!!, color = colorScheme.error)
                        }
                    },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    colors = OutlinedTextFieldDefaults.colors(

                        focusedContainerColor = colorScheme.surface,
                        unfocusedContainerColor = colorScheme.surface,

                        focusedLabelColor = colorScheme.onPrimary,
                        unfocusedLabelColor = colorScheme.tertiary,

                        focusedBorderColor = colorScheme.onPrimary,
                        unfocusedBorderColor = colorScheme.onPrimary,

                        cursorColor = colorScheme.onPrimary
                    ),
                    leadingIcon = {
                        Icon(
                            painter = rememberVectorPainter(image = Icons.Default.Email),
                            contentDescription = "Email icon",
                            tint = colorScheme.onSurfaceVariant
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
                            Text(text = passwordError!!, color = colorScheme.error)
                        }
                    },
                    label = { Text("Пароль") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation(),
                    colors = OutlinedTextFieldDefaults.colors(

                        focusedContainerColor = colorScheme.surface,
                        unfocusedContainerColor = colorScheme.surface,

                        focusedLabelColor = colorScheme.onPrimary,
                        unfocusedLabelColor = colorScheme.tertiary,

                        focusedBorderColor = colorScheme.onPrimary,
                        unfocusedBorderColor = colorScheme.onPrimary,

                        cursorColor = colorScheme.onPrimary
                    ),
                    leadingIcon = {
                        Icon(
                            painter = rememberVectorPainter(image = Icons.Default.Lock),
                            contentDescription = "Password icon",
                            tint = colorScheme.onSurfaceVariant
                        )
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        scope.launch {
                            viewModel.logInUser(email, password)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.secondary,
                        contentColor = colorScheme.onSecondary
                    )
                ) {
                    Text("Войти")
                }
            } else {
                // Форма для регистрации
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Ваше имя") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(


                        focusedContainerColor = colorScheme.surface,
                        unfocusedContainerColor = colorScheme.surface,

                        focusedLabelColor = colorScheme.onPrimary,
                        unfocusedLabelColor = colorScheme.tertiary,

                        focusedBorderColor = colorScheme.onPrimary,
                        unfocusedBorderColor = colorScheme.onPrimary,

                        cursorColor = colorScheme.onPrimary
                    ),
                    leadingIcon = {
                        Icon(
                            painter = rememberVectorPainter(image = Icons.Default.Person),
                            contentDescription = "Person icon",
                            tint = colorScheme.onSurfaceVariant
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
                            Text(text = passwordError!!, color = colorScheme.error)
                        }
                    },
                    label = { Text("Введите пароль") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(


                        focusedContainerColor = colorScheme.surface,
                        unfocusedContainerColor = colorScheme.surface,

                        focusedLabelColor = colorScheme.onPrimary,
                        unfocusedLabelColor = colorScheme.tertiary,

                        focusedBorderColor = colorScheme.onPrimary,
                        unfocusedBorderColor = colorScheme.onPrimary,

                        cursorColor = colorScheme.onPrimary
                    ),
                    leadingIcon = {
                        Icon(
                            painter = rememberVectorPainter(image = Icons.Default.Lock),
                            contentDescription = "Password icon",
                            tint = colorScheme.onSurfaceVariant
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
                            Text(text = phoneError!!, color = colorScheme.error)
                        }
                    },
                    label = { Text("Номер телефона") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    colors = OutlinedTextFieldDefaults.colors(


                        focusedContainerColor = colorScheme.surface,
                        unfocusedContainerColor = colorScheme.surface,

                        focusedLabelColor = colorScheme.onPrimary,
                        unfocusedLabelColor = colorScheme.tertiary,

                        focusedBorderColor = colorScheme.onPrimary,
                        unfocusedBorderColor = colorScheme.onPrimary,

                        cursorColor = colorScheme.onPrimary
                    ),
                    leadingIcon = {
                        Icon(
                            painter = rememberVectorPainter(image = Icons.Default.Phone),
                            contentDescription = "Phone icon",
                            tint = colorScheme.onSurfaceVariant
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
                            Text(text = emailError!!, color = colorScheme.error)
                        }
                    },
                    label = { Text("Email") },
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    colors = OutlinedTextFieldDefaults.colors(

                        focusedContainerColor = colorScheme.surface,
                        unfocusedContainerColor = colorScheme.surface,

                        focusedLabelColor = colorScheme.onPrimary,
                        unfocusedLabelColor = colorScheme.tertiary,

                        focusedBorderColor = colorScheme.onPrimary,
                        unfocusedBorderColor = colorScheme.onPrimary,

                        cursorColor = colorScheme.onPrimary
                    ),
                    leadingIcon = {
                        Icon(
                            painter = rememberVectorPainter(image = Icons.Default.Email),
                            contentDescription = "Email icon",
                            tint = colorScheme.onSurfaceVariant
                        )
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        scope.launch {
                            viewModel.registerUser(
                                email = email,
                                password=password,
                                phoneNumber = phone,
                                name = name,
                                )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.secondary,
                        contentColor = colorScheme.onSecondary
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
    isDarkTheme: Boolean
) {
    val backgroundColor = if (isSelected) {
        if (isDarkTheme) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.secondary
    } else {
        if (isDarkTheme) MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.primary
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

