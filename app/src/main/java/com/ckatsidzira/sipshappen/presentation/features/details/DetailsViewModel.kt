package com.ckatsidzira.sipshappen.presentation.features.details

import androidx.lifecycle.SavedStateHandle
import com.ckatsidzira.sipshappen.data.network.ConnectivityModule
import com.ckatsidzira.sipshappen.domain.Resource
import com.ckatsidzira.sipshappen.domain.base.BaseViewModel
import com.ckatsidzira.sipshappen.domain.mapper.toUiModel
import com.ckatsidzira.sipshappen.domain.repository.BeerRepository
import com.ckatsidzira.sipshappen.presentation.navigation.NavScreen.DetailArgs.ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: BeerRepository,
    private val connectivityModule: ConnectivityModule,
) : BaseViewModel<DetailsUiState, DetailsUiEvent, DetailsUiAction>() {
    private val _uiState: MutableStateFlow<DetailsUiState> = MutableStateFlow(DetailsUiState())
    override val uiState: StateFlow<DetailsUiState>
        get() = _uiState.asStateFlow()

    private val id: Int =
        savedStateHandle[ID] ?: throw IllegalArgumentException("Missing beer ID for details")

    init {
        getBeer()
    }

    override fun handleAction(action: DetailsUiAction) {
        TODO("Not yet implemented")
    }

    private fun getBeer() {
        safeLaunch(Dispatchers.IO) {
            connectivityModule.isConnected.collectLatest { isConnected ->
                if (!isConnected) {
                    _uiState.update { it.copy(screenData = ScreenData.Offline) }
                } else {
                    when (val resource = repository.getBeerById(id)) {
                        is Resource.Error -> updateState(ScreenData.Error(resource.message ?: "Something went wrong"))
                        is Resource.Loading -> updateState(ScreenData.Loading)
                        is Resource.Success -> resource.data?.let {
                            updateState(ScreenData.Data(beer = it.toUiModel()))
                        }
                    }
                }
            }
        }
    }

    private fun updateState(screenData: ScreenData = _uiState.value.screenData) {
        _uiState.update { currentState ->
            currentState.copy(
                screenData = screenData
            )
        }
    }
}