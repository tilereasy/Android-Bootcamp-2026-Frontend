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
import ru.sicampus.bootcamp2026.ui.components.CustomButton
import ru.sicampus.bootcamp2026.ui.components.CustomTextField


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen (
    onEnterClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {}
) {

    var email by remember { mutableStateOf("") }
    var password by remember {mutableStateOf("")}


    Scaffold(
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(200.dp))

            // Название приложения
            Text(
                text = "neRakov MEET",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.open_sans_bold))
            )

            Spacer(modifier = Modifier.height(52.dp))

            // Электронная почта
            CustomTextField(
                value = email,
                onValueChange = { email = it },
                label = "Электронная почта",
                TextGrey
            )

            Spacer(modifier = Modifier.height(16.dp))


            // Введите пароль
            CustomTextField(
                value = password,
                onValueChange = { password = it },
                label = "Пароль",
                TextGrey
            )

            Spacer(modifier = Modifier.height(57.dp))


            // Кнопка входа
            CustomButton(
                "Войти",
                DarkBlue,
                White,
                onEnterClick,
                Modifier
                    .padding(start = 64.dp, end = 64.dp)
                    .fillMaxWidth()
                    .height(36.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Кнопка регистрации
            CustomButton(
                "Зарегистрироваться",
                Grey,
                Black,
                onEnterClick,
                Modifier
                    .padding(start = 64.dp, end = 64.dp)
                    .fillMaxWidth()
                    .height(36.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}


@Preview
@Composable
fun ShowAuthScreen(){
    AppTheme {
        AuthScreen()
    }
}