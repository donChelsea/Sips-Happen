package com.ckatsidzira.sipshappen.presentation.features.home

import com.ckatsidzira.sipshappen.data.network.ConnectivityModule
import com.ckatsidzira.sipshappen.domain.Resource
import com.ckatsidzira.sipshappen.domain.base.BaseViewModel
import com.ckatsidzira.sipshappen.domain.mapper.toUiModel
import com.ckatsidzira.sipshappen.domain.repository.BeerRepository
import com.ckatsidzira.sipshappen.presentation.model.BeerUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: BeerRepository,
    private val connectivityModule: ConnectivityModule,
) : BaseViewModel<HomeUiState, HomeUiEvent, HomeUiAction>() {

    private var currentData = ScreenData.Data()

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    override val uiState: StateFlow<HomeUiState>
        get() = _uiState.asStateFlow()

    init {
        observeBeers()
        loadRandomBeer()
    }

    override fun handleAction(action: HomeUiAction) {
        when (action) {
            is HomeUiAction.OnBeerClicked -> emitUiEvent(HomeUiEvent.OnBeerClicked(action.beer))
            HomeUiAction.OnViewAllClicked -> emitUiEvent(HomeUiEvent.OnViewAllClicked)
        }
    }

    private fun observeBeers() {
        safeLaunch(Dispatchers.IO) {
            combine(
                connectivityModule.isConnected,
                repository.getBeersFlow()
            ) { isConnected, resource ->
                when {
                    !isConnected -> ScreenData.Offline
                    resource is Resource.Loading -> ScreenData.Loading
                    resource is Resource.Error -> ScreenData.Error(resource.message ?: "Something went wrong.")
                    resource is Resource.Success -> {
                        val beers = resource.data?.map { it.toUiModel() }.orEmpty()
                        currentData = currentData.copy(beers = beers)
                        currentData
                    }
                    else -> currentData
                }
            }.collectLatest { screenData ->
                _uiState.value = HomeUiState(screenData = screenData)
            }
        }
    }

    private fun loadRandomBeer() {
        safeLaunch {
            val beer = repository.getRandomBeer().toUiModel()
            currentData = currentData.copy(randomBeer = beer)
            _uiState.value = HomeUiState(screenData = currentData)
        }
    }

//    private fun getBeers() {
//        safeLaunch(Dispatchers.IO) {
//            combine(
//                connectivityModule.isConnected,
//                repository.getBeersFlow(),
//            ) { isConnected, result ->
//                if (!isConnected) {
//                    ScreenData.Offline
//                } else {
//                    if (result.data.orEmpty().isNotEmpty()) {
//                        ScreenData.Data(
//                            result.data.orEmpty().map { it.toUiModel() }
//                        )
//                    } else {
//                        ScreenData.Loading
//                    }
//                }
//            }
//                .catch { throwable ->
//                    emit(ScreenData.Error(throwable.localizedMessage ?: "Something went wrong."))
//                }.collectLatest {
//                    println(it)
//                    updateState(screenData = it)
//                }
//        }
//    }
//
//    private fun getRandomBeer() {
//        safeLaunch {
//            when (val result = repository.getRandomBeer()) {
//                is Resource.Success -> {
//                    val beerUi = result.data?.toUiModel() ?: BeerUiModel()
//                    updateState(ScreenData.Data(randomBeer = beerUi))
//                }
//                is Resource.Error -> {
//                    updateState(ScreenData.Error(result.message ?: "Something went wrong"))
//                }
//                is Resource.Loading -> {
//                    updateState(ScreenData.Loading)
//                }
//            }
//        }
//    }


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