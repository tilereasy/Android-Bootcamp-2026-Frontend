package ru.sicampus.bootcamp2026.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import ru.sicampus.bootcamp2026.R
import ru.sicampus.bootcamp2026.ui.theme.*

@Composable
fun CustomNavigationBar(
    selectedItem: Int = 0,
    onHomeClick: () -> Unit,
    onCreateMeetingClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(79.dp),
        containerColor = White,
        tonalElevation = 8.dp
    ) {
        // Иконка "Главная" (index 0)
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Home",
                    modifier = Modifier.size(45.dp),
                    tint = if (selectedItem == 0) DarkBlue else IconsGrey
                )
            },
            label = { Text("") },
            selected = selectedItem == 0,
            onClick = onHomeClick,
            modifier = Modifier.weight(1f)
        )

        // Иконка "Создать" (index 1)
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.AddCircleOutline,
                    contentDescription = "Create",
                    modifier = Modifier.size(45.dp),
                    tint = if (selectedItem == 1) DarkBlue else IconsGrey
                )
            },
            label = { Text("") },
            selected = selectedItem == 1,
            onClick = onCreateMeetingClick,
            modifier = Modifier.weight(1f)
        )

        // Иконка "Профиль" (index 2)
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile",
                    modifier = Modifier.size(45.dp),
                    tint = if (selectedItem == 2) DarkBlue else IconsGrey
                )
            },
            label = { Text("") },
            selected = selectedItem == 2,
            onClick = onProfileClick,
            modifier = Modifier.weight(1f)
        )
    }
}