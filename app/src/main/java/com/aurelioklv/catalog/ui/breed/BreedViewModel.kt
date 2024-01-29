package com.aurelioklv.catalog.ui.breed

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
class BreedViewModel @Inject constructor(private val catRepository: CatRepository) : ViewModel() {
    private val _state = MutableStateFlow(BreedScreenState())
    val state = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), BreedScreenState())

    init {
        onEvent(BreedScreenEvent.GetBreeds)
    }

    fun onEvent(event: BreedScreenEvent) {
        when (event) {
            BreedScreenEvent.HideBreedDetails -> {
                _state.update {
                    it.copy(
                        isShowingDetails = false
                    )
                }
            }

            is BreedScreenEvent.ShowBreedDetails -> {
                _state.update {
                    it.copy(
                        isShowingDetails = true, currentBreed = event.breed
                    )
                }
            }

            BreedScreenEvent.GetBreeds -> {
                getBreeds()
            }

            is BreedScreenEvent.GetBreed -> {
                _state.update {
                    it.copy(
                        currentBreed = null,
                        currentBreedCatRef = null
                    )
                }
                viewModelScope.launch {
                    getBreedById(event.id)
                }
            }
        }
    }

    private fun getBreeds() {
        viewModelScope.launch {
            var isError = false
            val breeds = try {
                catRepository.getBreeds()
            } catch (e: IOException) {
                e.printStackTrace()
                isError = true
                emptyList()
            } catch (e: HttpException) {
                e.printStackTrace()
                isError = true
                emptyList()
            }
            if (breeds.isNotEmpty()) {
                getBreedById(breeds.first().id)
            }
            _state.update {
                it.copy(
                    breeds = breeds,
                    isError = isError,
                    isLoading = false
                )
            }
        }
    }

    private fun getBreedById(id: String) {
        viewModelScope.launch {
            var isError = false
            var breed = try {
                catRepository.getBreedById(id)
            } catch (e: IOException) {
                e.printStackTrace()
                isError = true
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                isError = true
                null
            }
            var cat = try {
                breed?.referenceImageId?.let { catRepository.getCatById(it) }
            } catch (e: IOException) {
                e.printStackTrace()
                isError = true
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                isError = true
                null
            }
            _state.update {
                it.copy(
                    currentBreed = breed,
                    currentBreedCatRef = cat,
                    isError = isError,
                    isLoading = false
                )
            }
        }
    }
}