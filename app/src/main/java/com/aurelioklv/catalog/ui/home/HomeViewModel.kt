package com.aurelioklv.catalog.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.aurelioklv.catalog.CatalogApplication
import com.aurelioklv.catalog.data.model.Cat
import com.aurelioklv.catalog.data.repository.CatsRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface HomeUiState {
    data class Success(val cats: List<Cat>) : HomeUiState
    data object Error : HomeUiState
    data object Loading : HomeUiState
}

class HomeViewModel(private val catsRepository: CatsRepository) : ViewModel() {
    var uiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getCats()
    }

    fun getCats() {
        viewModelScope.launch {
            uiState = try {
                HomeUiState.Success(catsRepository.getCats())
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CatalogApplication)
                val catsRepository = application.container.catsRepository
                HomeViewModel(catsRepository = catsRepository)
            }
        }
    }
}