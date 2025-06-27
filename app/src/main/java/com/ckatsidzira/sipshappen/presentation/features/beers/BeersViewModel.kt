@file:OptIn(ExperimentalCoroutinesApi::class)

package com.ckatsidzira.sipshappen.presentation.features.beers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.ckatsidzira.sipshappen.data.network.ConnectivityObserver
import com.ckatsidzira.sipshappen.domain.mapper.toUiModel
import com.ckatsidzira.sipshappen.domain.repository.BeerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class BeersViewModel @Inject constructor(
    private val repository: BeerRepository,
    private val connectivityObserver: ConnectivityObserver,
): ViewModel() {

    val beerFlow = connectivityObserver.isConnected
        .flatMapLatest { isConnected ->
            if (isConnected) {
                repository
                    .getBeersPagingFlow()
                    .cachedIn(viewModelScope)
                    .map { pagingData ->
                        pagingData.map { it.toUiModel() }
                    }
            } else {
                flowOf(PagingData.empty())
            }
        }

    fun handleAction() {

    }
}