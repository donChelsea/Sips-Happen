package com.ckatsidzira.sipshappen.presentation.features.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ckatsidzira.sipshappen.presentation.custom.BannerSection
import com.ckatsidzira.sipshappen.presentation.custom.card.RandomBeerCard
import com.ckatsidzira.sipshappen.presentation.custom.card.BeerItemCard
import com.ckatsidzira.sipshappen.presentation.custom.state.ShowError
import com.ckatsidzira.sipshappen.presentation.custom.state.ShowLoading
import com.ckatsidzira.sipshappen.presentation.custom.state.ShowOffline
import com.ckatsidzira.sipshappen.presentation.features.home.HomeUiAction
import com.ckatsidzira.sipshappen.presentation.features.home.HomeUiEvent
import com.ckatsidzira.sipshappen.presentation.features.home.HomeUiState
import com.ckatsidzira.sipshappen.presentation.features.home.HomeViewModel
import com.ckatsidzira.sipshappen.presentation.features.home.ScreenData
import com.ckatsidzira.sipshappen.presentation.model.BeerUiModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.events.collectLatest { event ->
            when (event) {
                is HomeUiEvent.OnBeerClicked -> println("Beer: ${event.beer.name} clicked")
                HomeUiEvent.OnViewAllClicked -> println("View All clicked")
            }
        }
    }

    HomeLayout(
        modifier = modifier,
        state = state,
        onAction = viewModel::handleAction,
    )
}

@Composable
fun HomeLayout(
    modifier: Modifier = Modifier,
    state: HomeUiState,
    onAction: (HomeUiAction) -> Unit
) {
    when (state.screenData) {
        is ScreenData.Data -> HomeContent(
            modifier = modifier,
            beers = state.screenData.beers,
            randomBeer = state.screenData.randomBeer,
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
    modifier: Modifier = Modifier,
    beers: List<BeerUiModel>,
    randomBeer: BeerUiModel,
    onAction: (HomeUiAction) -> Unit
) {
    Column(modifier = modifier) {
        BannerSection(
            title = "Today's choice",
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp),
        )

        RandomBeerCard(
            beer = randomBeer,
            modifier = Modifier.padding(horizontal = 18.dp),
            onItemClick = { onAction(HomeUiAction.OnBeerClicked(it)) }
        )

        Spacer(modifier = Modifier.height(42.dp))

        BannerSection(
            title = "Top picks",
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp),
            onClick = { onAction(HomeUiAction.OnViewAllClicked) },
            onClickText = "View All"
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 10.dp),
        ) {
            items(
                items = beers,
                key = { it.id }
            ) { beer ->
                BeerItemCard(
                    beer = beer,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onItemClick = { onAction(HomeUiAction.OnBeerClicked(it)) }
                )
            }
        }
    }
}