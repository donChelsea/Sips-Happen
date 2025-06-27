package com.ckatsidzira.sipshappen.presentation.features.beers

import androidx.compose.runtime.Immutable
import com.ckatsidzira.sipshappen.presentation.model.BeerUiModel

@Immutable
data class BeersUiState(
    val screenData: ScreenData = ScreenData.Loading
)

@Immutable
sealed class BeersUiEvent {
    data class OnBeerClicked(val id: Int) : BeersUiEvent()
}

@Immutable
sealed class BeersUiAction {
    data class OnBeerClicked(val id: Int) : BeersUiAction()
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