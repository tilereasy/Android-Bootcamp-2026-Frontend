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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.modifier.modifierLocalMapOf
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

    // Иконка уведов есть/нет
    var hasInvites by remember { mutableStateOf(true) }

    // Встречи есть/нет
    var hasMeetings by remember { mutableStateOf(false) }

    var showViewTypeMenu by remember { mutableStateOf(true) }


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
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.open_sans_semibold)),
                            color = DarkBlue
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
                                        .offset(x = (-2).dp, y = (-2).dp)
                                        .background(
                                            color = Color.Red,
                                            shape = RoundedCornerShape(50.dp)
                                        )
                                        .size(10.dp)
                                )
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notifications",
                                tint = DarkBlue,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    } else {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = IconsGrey,
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


// ЕСЛИ НЕТ ВСТРЕЧ
@Composable
fun EmptyMeetingsState(onCreateMeetingClick: () -> Unit){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.SearchOff,
            //painter = painterResource(id = R.drawable.lupa),
            contentDescription = "No meetings",
            modifier = Modifier.size(150.dp),
            tint = Black
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "У вас пока нет встреч",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.open_sans_semibold)),
            color = DarkBlue,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(5.dp))

        // Возможно переделать этот текст под кнопку, если будет время
        Text(
            text = "Запланировать?",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.open_sans_extrabold)),
            color = DarkBlue,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
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

// Bottom navigation Bar
@Composable
fun BottomNavigationBarHome(
    onHomeClick: () -> Unit = {},
    onCreateClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
){
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(79.dp),
        containerColor = White,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Home",
                    modifier = Modifier.size(45.dp),
                    tint = DarkBlue
                )
            },
            label = { Text("") },
            selected = true,
            onClick = onHomeClick,
            modifier = Modifier.weight(1f)
        )

        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.AddCircleOutline,
                    contentDescription = "Create",
                    modifier = Modifier.size(45.dp),
                    tint = IconsGrey
                )
            },
            label = { Text("") },
            selected = false,
            onClick = onCreateClick,
            modifier = Modifier.weight(1f)
        )

        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile",
                    modifier = Modifier.size(45.dp),
                    tint = IconsGrey
                )
            },
            label = { Text("") },
            selected = false,
            onClick = onProfileClick,
            modifier = Modifier.weight(1f)
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