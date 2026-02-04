package ru.sicampus.bootcamp2026.android.ui.testScreens.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun ListScreen(
    viewModel: ListViewModel = viewModel<ListViewModel>(),
    navController: NavController,
) {
    val state by viewModel.uiState.collectAsState()

    when (val currentState = state) {
        is ListState.Error -> ListErrorState(currentState, onRefresh = { viewModel.getData() })
        is ListState.Loading -> ListLoadingState()
        is ListState.Content -> ListContentState(currentState)
    }
}

@Composable
private fun ListLoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(48.dp)
        )
    }
}

@Composable
private fun ListErrorState(
    state: ListState.Error,
    onRefresh: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(state.reason)
            Button(
                onClick = onRefresh
            ) {
                Text("Refresh")
            }
        }
    }
}

@Composable
private fun ListContentState(
    state: ListState.Content,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        state.users.forEach { user ->

            Column {
                Text(user.name)
                Text(user.email)
            }

        }
    }
}