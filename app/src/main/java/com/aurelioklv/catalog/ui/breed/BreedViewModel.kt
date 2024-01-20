package com.aurelioklv.catalog.ui.breed

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aurelioklv.catalog.domain.repository.CatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class BreedViewModel @Inject constructor(private val catRepository: CatRepository) : ViewModel() {
    var uiState: BreedUiState by mutableStateOf(BreedUiState.Loading)
        private set

    init {
        getBreeds()
    }

    fun getBreeds() {
        viewModelScope.launch {
            uiState = try {
                BreedUiState.Success(catRepository.getBreeds())
            } catch (e: IOException) {
                BreedUiState.Error
            } catch (e: HttpException) {
                BreedUiState.Error
            }
        }
    }
}