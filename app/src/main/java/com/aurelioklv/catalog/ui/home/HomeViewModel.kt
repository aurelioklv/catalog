package com.aurelioklv.catalog.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aurelioklv.catalog.domain.repository.CatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val catRepository: CatRepository) : ViewModel() {
    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), HomeScreenState())

    init {
        _state.update {
            it.copy(numberOfCat = 10)
        }
        onEvent(HomeScreenEvent.RefreshImage)
    }

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            HomeScreenEvent.HideCatDetails -> {
                _state.update {
                    it.copy(isShowingDetails = false)
                }
            }

            HomeScreenEvent.RefreshImage -> {
                getCats(_state.value.numberOfCat, _state.value.hasBreeds)
            }

            is HomeScreenEvent.SaveCatImage -> TODO()
            is HomeScreenEvent.ShowCatDetails -> {
                _state.update {
                    it.copy(isShowingDetails = true, currentCat = event.cat)
                }
            }
        }
    }

    fun getCats(limit: Int, hasBreeds: Int) {
        viewModelScope.launch {
            var isError = false
            val cats = try {
                catRepository.getCats(limit, hasBreeds)
            } catch (e: IOException) {
                e.printStackTrace()
                isError = true
                emptyList()
            } catch (e: HttpException) {
                e.printStackTrace()
                isError = true
                emptyList()
            }
            _state.update {
                it.copy(
                    cats = cats,
                    isError = isError,
                    isLoading = false
                )
            }
        }
    }
}