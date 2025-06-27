package com.ckatsidzira.sipshappen.presentation.features.details

import com.ckatsidzira.sipshappen.presentation.model.BeerUiModel
import javax.annotation.concurrent.Immutable

data class DetailsUiState(
    val screenData: ScreenData = ScreenData.Loading
)

@Immutable
sealed class DetailsUiEvent {}

@Immutable
sealed class DetailsUiAction {}

@Immutable
sealed class ScreenData {
    data object Loading : ScreenData()
    data object Offline : ScreenData()

    @Immutable
    data class Error(val message: String) : ScreenData()

    @Immutable
    data class Data(
        val beer: BeerUiModel = BeerUiModel(),
    ) : ScreenData()
}