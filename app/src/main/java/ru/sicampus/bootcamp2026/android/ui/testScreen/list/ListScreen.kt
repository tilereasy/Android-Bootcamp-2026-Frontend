package ru.sicampus.bootcamp2026.android.ui.testScreen.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ListScreen(
    viewModel: ListViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    when (val currentState = state) {
        is ListState.Content -> ListContentState(currentState)
        is ListState.Error -> ListErrorState(currentState, onRefresh = { viewModel.getData() })
        ListState.Loading -> ListLoadingState()
    }
}

@Composable
private fun ListLoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.size(48.dp))
    }
}

@Composable
private fun ListErrorState(
    state: ListState.Error,
    onRefresh: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(state.reason)
            Spacer(Modifier.height(8.dp))
            Button(onClick = onRefresh) { Text("Обновить") }
        }
    }
}

@Composable
private fun ListContentState(state: ListState.Content) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(state.users) { user ->
            Card {
                Column(Modifier.padding(12.dp)) {
                    Text(user.name, style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(4.dp))
                    Text(user.email, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}