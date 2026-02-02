package ru.sicampus.bootcamp2026.android.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import ru.sicampus.bootcamp2026.R
import ru.sicampus.bootcamp2026.android.ui.theme.Black
import ru.sicampus.bootcamp2026.android.ui.theme.Grey


@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    textColor: Color
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                fontFamily = FontFamily(Font(R.font.open_sans_semibold)),
                color = textColor
            )
        },
        modifier = Modifier
            .padding(start = 25.dp, end = 25.dp)
            .fillMaxWidth(),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedTextColor = Black,
            unfocusedTextColor = Black,
            focusedContainerColor = Grey,
            unfocusedContainerColor = Grey,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(40.dp)
    )
}