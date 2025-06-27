package com.ckatsidzira.sipshappen.presentation.features.beers.ui


import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ckatsidzira.sipshappen.presentation.custom.card.BeerItemCard
import com.ckatsidzira.sipshappen.presentation.custom.liststate.ErrorIndicator
import com.ckatsidzira.sipshappen.presentation.custom.liststate.LoadingIndicator
import com.ckatsidzira.sipshappen.presentation.features.beers.BeersViewModel
import com.ckatsidzira.sipshappen.presentation.model.BeerUiModel

@Composable
fun BeersScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: BeersViewModel = hiltViewModel(),
) {
    val beers: LazyPagingItems<BeerUiModel> = viewModel.beerFlow.collectAsLazyPagingItems()
    val context = LocalContext.current
    val lazyListState = rememberLazyListState()

    LaunchedEffect(key1 = beers.loadState) {
        if (beers.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: ${(beers.loadState.refresh as LoadState.Error).error.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    BeersContent(
        modifier = modifier,
        lazyListState = lazyListState,
        beers = beers
    )
}

@Composable
fun BeersContent(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    beers: LazyPagingItems<BeerUiModel>,
) {
    LazyColumn(
        modifier = modifier,
        state = lazyListState,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(beers.itemCount) { index ->
            val beer = beers[index]
            beer?.let {
                BeerItemCard(it)
            }
        }

        when (beers.loadState.refresh) {
            is LoadState.Loading -> {
                item {
                    LoadingIndicator(text = "Loading beers...")
                }
            }

            is LoadState.Error -> {
                item {
                    ErrorIndicator(message = "Error loading data. Please try again.")
                }
            }

            else -> {}
        }

        when (beers.loadState.append) {
            is LoadState.Loading -> {
                item {
                    LoadingIndicator(text = "Loading more beers...")
                }
            }

            is LoadState.Error -> {
                item {
                    ErrorIndicator(message = "Error loading more data.")
                }
            }

            else -> {}
        }
    }
}