package ru.sicampus.bootcamp2026.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.sicampus.bootcamp2026.ui.theme.Black
import ru.sicampus.bootcamp2026.ui.theme.DarkBlue

@Composable
fun ProfileField(
    label: String,
    value: String,
    isEditable: Boolean
){
    var text by remember{ mutableStateOf(value) }
    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = {
            Text(label)
        },
        colors = TextFieldDefaults.colors(
            focusedTextColor = Black,
            unfocusedTextColor = Black,
            disabledTextColor = Black,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = if (isEditable) DarkBlue else Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = Black,
        ),
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )

}
