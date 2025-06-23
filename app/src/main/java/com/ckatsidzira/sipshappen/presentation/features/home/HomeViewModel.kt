package com.ckatsidzira.sipshappen.presentation.features.home

import com.ckatsidzira.sipshappen.data.network.ConnectivityModule
import com.ckatsidzira.sipshappen.domain.Resource
import com.ckatsidzira.sipshappen.domain.base.BaseViewModel
import com.ckatsidzira.sipshappen.domain.mapper.toUiModel
import com.ckatsidzira.sipshappen.domain.repository.BeerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: BeerRepository,
    private val connectivityModule: ConnectivityModule,
) : BaseViewModel<HomeUiState, HomeUiEvent, HomeUiAction>() {

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    override val uiState: StateFlow<HomeUiState>
        get() = _uiState.asStateFlow()

    init {
        getBeers()
    }

    override fun handleAction(action: HomeUiAction) {
        when (action) {
            is HomeUiAction.OnBeerClicked -> emitUiEvent(HomeUiEvent.OnBeerClicked(action.id))
        }
    }

    private fun getBeers() {
        safeLaunch(Dispatchers.IO) {
            combine(
                connectivityModule.isConnected,
                repository.getBeersFlow()
            ) { isConnected, result ->
                if (!isConnected) {
                    ScreenData.Offline
                } else {
                    when (result) {
                        is Resource.Error -> ScreenData.Error(
                            result.message ?: "An error occurred."
                        )
                        is Resource.Loading -> ScreenData.Loading
                        is Resource.Success -> ScreenData.Data(
                            beers = result.data.orEmpty().map { it.toUiModel() }
                        )
                    }
                }
            }.collectLatest {
                println(it)
                updateState(screenData = it)
            }
        }
    }

    private fun updateState(
        screenData: ScreenData = _uiState.value.screenData
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                screenData = screenData
            )
        }
    }

}