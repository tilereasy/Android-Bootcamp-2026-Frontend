package ru.sicampus.bootcamp2026

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import ru.sicampus.bootcamp2026.android.ui.screens.AuthScreen
import ru.sicampus.bootcamp2026.android.ui.testScreen.list.ListScreen
import ru.sicampus.bootcamp2026.android.ui.testScreen.list.ListViewModel
import ru.sicampus.bootcamp2026.android.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            Box(
//                Modifier.fillMaxSize()
//            )
//            {
//                ListScreen()
//            }
            AppTheme {
                Box(
                    Modifier.fillMaxSize(),
                ) {
                    ListScreen()
                }
            }

        }
    }
}