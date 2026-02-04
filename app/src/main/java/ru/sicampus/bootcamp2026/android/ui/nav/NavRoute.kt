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
import ru.sicampus.bootcamp2026.android.ui.testScreens.home.HomeScreen
import ru.sicampus.bootcamp2026.android.ui.testScreens.home.CreateMeetingScreen
import ru.sicampus.bootcamp2026.android.ui.testScreens.home.ProfileScreen

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val currentToken = runBlocking { AuthLocalDataSource.getToken() }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = if (currentToken == null) AuthRoute else HomeRoute
    ) {
        // Экран авторизации
        composable<AuthRoute> {
            AuthScreen(
                navController = navController
            )
        }

        // Главный экран
        composable<HomeRoute> {
            HomeScreen(
                navController = navController,
                onNotificationsClick = {}
            )
        }

        // Экран создания встречи
        composable<CreateMeetingRoute> {
            CreateMeetingScreen(
                navController = navController,
            )
        }

        // Экран профиля
        composable<ProfileRoute> {
            ProfileScreen(
                navController = navController,
                onEditClick = {},
                onExitClick = {},
            )
        }
    }
}

fun NavHostController.navigateBottom(route: Any) {
    navigate(route) {
        popUpTo(graph.startDestinationId) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}