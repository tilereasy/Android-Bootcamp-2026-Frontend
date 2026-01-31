package ru.sicampus.bootcamp2026.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.sicampus.bootcamp2026.ui.theme.*
import ru.sicampus.bootcamp2026.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogScreen(
    onBackClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {}
) {

    var email by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var department by remember { mutableStateOf("") }
    var position by remember { mutableStateOf("") }
    var createPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Scaffold(
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(R.drawable.arrow_icon),
                contentDescription = "Назад",
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 46.dp, start = 24.dp)
            )

            Spacer(modifier = Modifier.height(87.dp))

            Text(
                text = "Регистрация",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.open_sans_bold))
            )

            Spacer(modifier = Modifier.height(38.dp))

            TextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = {
                    Text(
                        text = "ФИО",
                        fontFamily = FontFamily(Font(R.font.open_sans_semibold)),
                        color = TextGrey
                    )
                        } ,
                modifier = Modifier
                    .padding(start = 25.dp, end = 25.dp)
                    .fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Black,
                    unfocusedTextColor = Black,
                    focusedContainerColor = Grey,
                    unfocusedContainerColor = Grey,
                    focusedBorderColor = Color.Transparent, // Убираем рамку
                    unfocusedBorderColor = Color.Transparent // Убираем рамку
                ),
                shape = RoundedCornerShape(10.dp)
            )


            Spacer(modifier = Modifier.height(16.dp))

            // Отдел
            TextField(
                value = department,
                onValueChange = { department = it },
                label = {
                    Text(
                        text = "Отдел",
                        fontFamily = FontFamily(Font(R.font.open_sans_semibold)),
                        color = TextGrey
                    )
                } ,
                modifier = Modifier
                    .padding(start = 25.dp, end = 25.dp)
                    .fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Black,
                    unfocusedTextColor = Black,
                    focusedContainerColor = Grey,
                    unfocusedContainerColor = Grey,
                    focusedBorderColor = Color.Transparent, // Убираем рамку
                    unfocusedBorderColor = Color.Transparent // Убираем рамку
                ),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Должность
            TextField(
                value = position,
                onValueChange = { position = it },
                label = {
                    Text(
                        text = "Должность",
                        fontFamily = FontFamily(Font(R.font.open_sans_semibold)),
                        color = TextGrey
                    )
                } ,
                modifier = Modifier
                    .padding(start = 25.dp, end = 25.dp)
                    .fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Black,
                    unfocusedTextColor = Black,
                    focusedContainerColor = Grey,
                    unfocusedContainerColor = Grey,
                    focusedBorderColor = Color.Transparent, // Убираем рамку
                    unfocusedBorderColor = Color.Transparent // Убираем рамку
                ),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))


            // Электронная почта
            TextField(
                value = email,
                onValueChange = { email = it },
                label = {
                    Text(
                        text = "Электронная почта",
                        fontFamily = FontFamily(Font(R.font.open_sans_semibold)),
                        color = TextGrey
                    )
                } ,
                modifier = Modifier
                    .padding(start = 25.dp, end = 25.dp)
                    .fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Black,
                    unfocusedTextColor = Black,
                    focusedContainerColor = Grey,
                    unfocusedContainerColor = Grey,
                    focusedBorderColor = Color.Transparent, // Убираем рамку
                    unfocusedBorderColor = Color.Transparent // Убираем рамку
                ),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Придумайте пароль
            TextField(
                value = createPassword,
                onValueChange = { createPassword = it },
                label = {
                    Text(
                        text = "Придумайте пароль",
                        fontFamily = FontFamily(Font(R.font.open_sans_semibold)),
                        color = TextGrey
                    )
                } ,
                modifier = Modifier
                    .padding(start = 25.dp, end = 25.dp)
                    .fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Black,
                    unfocusedTextColor = Black,
                    focusedContainerColor = Grey,
                    unfocusedContainerColor = Grey,
                    focusedBorderColor = Color.Transparent, // Убираем рамку
                    unfocusedBorderColor = Color.Transparent // Убираем рамку
                ),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Подтвердите пароль
            TextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = {
                    Text(
                        text = "Подтвердите пароль",
                        fontFamily = FontFamily(Font(R.font.open_sans_semibold)),
                        color = TextGrey
                    )
                } ,
                modifier = Modifier
                    .padding(start = 25.dp, end = 25.dp)
                    .fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Black,
                    unfocusedTextColor = Black,
                    focusedContainerColor = Grey,
                    unfocusedContainerColor = Grey,
                    focusedBorderColor = Color.Transparent, // Убираем рамку
                    unfocusedBorderColor = Color.Transparent // Убираем рамку
                ),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(57.dp))

            // Кнопка регистрации
            Button(
                onClick = onRegisterClick,
                modifier = Modifier
                    .padding(start = 64.dp, end = 64.dp)
                    .fillMaxWidth()
                    .height(36.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkBlue
                )
            ) {
                Text(
                    text = "Зарегистрироваться",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.open_sans_semibold)),
                    color = White,
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview
@Composable
fun ShowLogScreen(){
    AppTheme {
        LogScreen()
    }
}