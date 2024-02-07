package com.aurelioklv.catalog.ui.home

import android.app.Application
import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.ImageRequest
import com.aurelioklv.catalog.R
import com.aurelioklv.catalog.data.network.model.NetworkCat
import com.aurelioklv.catalog.domain.repository.CatRepository
import com.aurelioklv.catalog.domain.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.io.OutputStream
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val application: Application,
    private val imageLoader: ImageLoader,
    private val catRepository: CatRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), HomeScreenState())

    init {
        viewModelScope.launch {
            userPreferencesRepository.userPreferences.take(1).collect { userPreferences ->
                _state.update {
                    it.copy(
                        fetchAmount = userPreferences.fetchAmount,
                        gridColumn = userPreferences.gridColumn,
                    )
                }
                onEvent(HomeScreenEvent.RefreshImage)
            }

            userPreferencesRepository.userPreferences.collect { userPreferences ->
                _state.update {
                    it.copy(
                        fetchAmount = userPreferences.fetchAmount,
                        gridColumn = userPreferences.gridColumn,
                    )
                }
            }
        }
    }

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            HomeScreenEvent.HideCatDetails -> {
                _state.update {
                    it.copy(isShowingDetails = false)
                }
            }

            HomeScreenEvent.RefreshImage -> {
                getCats(limit = _state.value.fetchAmount, hasBreeds = _state.value.hasBreeds)
            }

            is HomeScreenEvent.SaveCatImage -> {
                downloadImage(event.networkCat)
            }

            is HomeScreenEvent.ShowCatDetails -> {
                _state.update {
                    it.copy(isShowingDetails = true, currentNetworkCat = event.networkCat)
                }
            }
        }
    }

    private fun getCats(limit: Int?, hasBreeds: Int) {
        viewModelScope.launch {
            var isError = false
            val cats = try {
                if (limit == null) {
                    catRepository.getCats(hasBreeds = hasBreeds)
                } else {
                    catRepository.getCats(limit = limit, hasBreeds = hasBreeds)
                }
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
                    networkCats = cats,
                    isError = isError,
                    isLoading = false
                )
            }
        }
    }

    private fun downloadImage(networkCat: NetworkCat) {
        viewModelScope.launch(Dispatchers.IO) {
            val request = ImageRequest.Builder(application)
                .data(networkCat.imageUrl)
                .build()

            val result = imageLoader.execute(request).drawable?.toBitmap()
            val breedName = when (networkCat.networkBreeds?.isNotEmpty()) {
                true -> networkCat.networkBreeds.first().name.replace(" ", "_")
                else -> "NA"
            }

            val imageName = "${breedName}_(${networkCat.id})"
            result?.let {
                Log.i("DOWNLOAD", "Bitmap: $it")
                saveImageToFile(it, imageName)
            }
        }
    }

    private fun saveImageToFile(bitmap: Bitmap, title: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val resolver = application.contentResolver
                val imageDir =
                    "${Environment.DIRECTORY_PICTURES}/${application.getString(R.string.app_name)}"
                val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                var stream: OutputStream? = null
                var uri: Uri? = null

                val contentValues = ContentValues().apply {
                    put(MediaStore.Images.ImageColumns.DISPLAY_NAME, title)
                    put(MediaStore.Images.ImageColumns.RELATIVE_PATH, imageDir)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                }

                try {
                    uri = resolver.insert(contentUri, contentValues)
                    if (uri == null) {
                        throw IOException("Failed to insert record.")
                    }

                    stream = resolver.openOutputStream(uri)
                    if (stream == null) {
                        throw IOException("Failed to open output stream.")
                    }

                    if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)) {
                        throw IOException("Failed to write bitmap.")
                    }
                } catch (e: IOException) {
                    Log.e("HomeViewModel", "IOException $e")
                    if (uri != null) {
                        resolver.delete(uri, null, null)
                    }
                } catch (e: Exception) {
                    Log.e("HomeViewModel", "Exception $e")
                    throw e
                } finally {
                    stream?.close()
                }
            }
        }
    }

    companion object {
        const val TAG = "HomeViewModel"
    }
}