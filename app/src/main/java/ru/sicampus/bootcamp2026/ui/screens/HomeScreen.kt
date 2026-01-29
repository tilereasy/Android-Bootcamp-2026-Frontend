package ru.sicampus.bootcamp2026.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
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
fun HomeScreen (
    onNotificationsClick: () -> Unit = {},
    onCreateMeetingClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
){

    var viewType by remember { mutableStateOf("День") }

    // Флаг для иконки уведомлений
    var hasInvites by remember { mutableStateOf(false) }

    // Флаг для встреч
    var hasMeetings by remember { mutableStateOf(false) }

    var showViewTypeMenu by remember { mutableStateOf(false) }


    Scaffold(
        bottomBar = {
            BottomNavigationBarHome(
                onHomeClick = {},
                onCreateClick = onCreateMeetingClick,
                onProfileClick = onProfileClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                // Выбор "день/неделя/месяц"
                Box(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = TextGrey,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable{showViewTypeMenu = !showViewTypeMenu}
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ){
                    Text(
                        text = viewType,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.open_sans_semibold)),
                            color = Black
                    )
                }

                // Меню выбора
                DropdownMenu(
                    expanded = showViewTypeMenu,
                    onDismissRequest = { showViewTypeMenu = false }
                ) {
                    listOf("День", "Неделя", "Месяц").forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                viewType = option
                                showViewTypeMenu = false
                            }
                        )
                    }
                }

                // Иконка уведомлений (меняется цвет если есть уведы)
                IconButton(onClick = onNotificationsClick) {
                    if (hasInvites) {
                        BadgedBox(
                            badge = {
                                Badge(
                                    modifier = Modifier
                                        .offset(x = (-8).dp, y = 8.dp)
                                        .background(
                                            color = Color.Red,
                                            shape = RoundedCornerShape(50.dp)
                                        )
                                        .size(8.dp)
                                )
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notifications",
                                tint = DarkBlue,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    } else {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = TextGrey,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

        // Основа, отображение встреч
            if(hasMeetings){
                //ЗАГЛУШКА
                MeetingsListPlaceholder()
            }
            else {
                EmptyMeetingsState(onCreateMeetingClick = onCreateMeetingClick)
            }
        }
    }
}


// НЕТ ВСТРЕЧ
@Composable
fun EmptyMeetingsState(){

}

// ЕСТЬ ВСТРЕЧИ, ЗАГЛУШКА
@Composable
fun MeetingsListPlaceholder() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Здесь будут встречи. Потом. Как говорил один из Буниных: Завтра",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.open_sans_semibold)),
            color = TextGrey
        )
    }
}



@Preview
@Composable
fun ShowHomeScreen(){
    AppTheme {
        HomeScreen()
    }
}