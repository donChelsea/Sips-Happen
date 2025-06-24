package com.ckatsidzira.sipshappen.presentation.features.home

import androidx.compose.runtime.Immutable
import com.ckatsidzira.sipshappen.presentation.model.BeerUiModel

@Immutable
data class HomeUiState(
    val screenData: ScreenData = ScreenData.Loading
)

@Immutable
sealed class HomeUiEvent {
    data class OnBeerClicked(val beer: BeerUiModel) : HomeUiEvent()
    data object OnViewAllClicked : HomeUiEvent()
}

@Immutable
sealed class HomeUiAction {
    data class OnBeerClicked(val beer: BeerUiModel) : HomeUiAction()
    data object OnViewAllClicked : HomeUiAction()
}

@Immutable
sealed class ScreenData {
    data object Loading : ScreenData()
    data object Offline : ScreenData()

    @Immutable
    data class Error(val message: String) : ScreenData()

    @Immutable
    data class Data(
        val beers: List<BeerUiModel> = emptyList(),
        val randomBeer: BeerUiModel = BeerUiModel(),
    ) : ScreenData()
}