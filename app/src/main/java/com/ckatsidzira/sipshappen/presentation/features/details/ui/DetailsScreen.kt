package com.ckatsidzira.sipshappen.presentation.features.details.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.ckatsidzira.sipshappen.presentation.custom.state.ShowError
import com.ckatsidzira.sipshappen.presentation.custom.state.ShowLoading
import com.ckatsidzira.sipshappen.presentation.custom.state.ShowOffline
import com.ckatsidzira.sipshappen.presentation.features.details.DetailsUiAction
import com.ckatsidzira.sipshappen.presentation.features.details.DetailsUiState
import com.ckatsidzira.sipshappen.presentation.features.details.DetailsViewModel
import com.ckatsidzira.sipshappen.presentation.features.details.ScreenData
import com.ckatsidzira.sipshappen.presentation.model.BeerUiModel

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    DetailsLayout(
        modifier = modifier,
        state = state,
        onAction = viewModel::handleAction,
    )
}

@Composable
fun DetailsLayout(
    modifier: Modifier,
    state: DetailsUiState,
    onAction: (DetailsUiAction) -> Unit,
) {
    when (val data = state.screenData) {
        is ScreenData.Data -> DetailsContent(
            modifier = modifier,
            beer = data.beer,
            onAction = onAction,
        )

        is ScreenData.Error -> ShowError(
            modifier = modifier,
            message = data.message
        )
        ScreenData.Loading -> ShowLoading()
        ScreenData.Offline -> ShowOffline()
    }
}

@Composable
fun DetailsContent(
    modifier: Modifier,
    beer: BeerUiModel,
    onAction: (DetailsUiAction) -> Unit,
) {
    Column(modifier = modifier) {
        Text(text = beer.name)
    }
}
