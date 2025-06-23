package com.ckatsidzira.sipshappen.presentation.features.home.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ckatsidzira.sipshappen.presentation.custom.ShowError
import com.ckatsidzira.sipshappen.presentation.custom.ShowLoading
import com.ckatsidzira.sipshappen.presentation.custom.ShowOffline
import com.ckatsidzira.sipshappen.presentation.features.home.HomeUiAction
import com.ckatsidzira.sipshappen.presentation.features.home.HomeUiState
import com.ckatsidzira.sipshappen.presentation.features.home.HomeViewModel
import com.ckatsidzira.sipshappen.presentation.features.home.ScreenData
import com.ckatsidzira.sipshappen.presentation.model.BeerUiModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    HomeLayout(
        modifier = modifier,
        state = state,
        onAction = viewModel::handleAction,
    )
}

@Composable
fun HomeLayout(
    modifier: Modifier,
    state: HomeUiState,
    onAction: (HomeUiAction) -> Unit
) {
    when (state.screenData) {
        is ScreenData.Data -> HomeContent(
            modifier = modifier,
            beers = state.screenData.beers,
            onAction = onAction
        )
        is ScreenData.Error -> ShowError(
            modifier = modifier,
            message = state.screenData.message
        )
        is ScreenData.Loading -> ShowLoading(modifier = modifier)
        is ScreenData.Offline -> ShowOffline(modifier = modifier)
    }
}

@Composable
fun HomeContent(
    modifier: Modifier,
    beers: List<BeerUiModel>,
    onAction: (HomeUiAction) -> Unit
) {
     LazyColumn(
         modifier = modifier,
     ) {
         items(
             items = beers,
             key = { it.id }
         ) { beer ->
             Text(text = beer.name)
         }
     }
}
