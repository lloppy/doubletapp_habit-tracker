package com.example.habittracker.ui.screens.sync

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habittracker.R
import com.example.habittracker.ui.AppViewModelProvider
import com.example.habittracker.ui.navigation.NavigationDestination
import com.example.habittracker.ui.screens.HabitAppBar
import com.example.habittracker.ui.theme.Spacing

object SyncDestination : NavigationDestination {
    override val route = "sync_destination"
    override val title = R.string.sync
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SyncScreen(
    onClickOpenDrawer: () -> Unit,
    viewModel: SyncScreenViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier,
) {
    val state = viewModel.state

    Scaffold(
        topBar = {
            HabitAppBar(
                title = stringResource(SyncDestination.title),
                canNavigateBack = false,
                onClickOpenDrawer = onClickOpenDrawer
            )
        },
        modifier = modifier
    ) { paddingValues ->
        SyncContent(
            state = state,
            onDownloadClick = { viewModel.syncFromRemoteToLocal() },
            onUploadClick = { viewModel.syncFromLocalToRemote() },
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun SyncContent(
    state: SyncScreenState,
    onDownloadClick: () -> Unit,
    onUploadClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Spacing.large),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SyncButton(
            text = stringResource(R.string.sync_download_from_server),
            onClick = onDownloadClick,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(Spacing.medium))

        SyncButton(
            text = stringResource(R.string.sync_upload_to_server),
            onClick = onUploadClick,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(Spacing.large))

        when (state) {
            is SyncScreenState.Loading -> CircularProgressIndicator()
            is SyncScreenState.Success -> SyncStatusText(
                text = state.message,
                isError = false
            )

            is SyncScreenState.Error -> SyncStatusText(
                text = state.message,
                isError = true
            )

            SyncScreenState.Idle -> Unit
        }
    }
}

@Composable
private fun SyncButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(text)
    }
}

@Composable
private fun SyncStatusText(
    text: String,
    isError: Boolean,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        color = if (isError) MaterialTheme.colorScheme.error
        else MaterialTheme.colorScheme.primary,
        modifier = modifier
    )
}