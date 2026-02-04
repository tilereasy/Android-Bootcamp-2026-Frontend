package ru.sicampus.bootcamp2026.android.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.runBlocking
import ru.sicampus.bootcamp2026.android.data.source.AuthLocalDataSource
import ru.sicampus.bootcamp2026.android.ui.testScreens.auth.AuthScreen
import ru.sicampus.bootcamp2026.android.ui.testScreens.list.ListScreen

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    // TODO: надо написать UseCase
    val currentToken = runBlocking { AuthLocalDataSource.getToken() }
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = if (currentToken == null) AuthRoute else ListRoute
    ) {
        composable<AuthRoute> {
            AuthScreen(
                navController = navController
            )
        }
        composable<ListRoute> {
            ListScreen(
                navController = navController
            )
        }
    }
}