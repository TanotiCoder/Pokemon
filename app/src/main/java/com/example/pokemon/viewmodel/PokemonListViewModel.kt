package com.example.pokemon.viewmodel

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.palette.graphics.Palette
import com.example.pokemon.model.PokemonResult
import com.example.pokemon.repository.PokemonRepository
import com.example.pokemon.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PokemonUIState(
    var isLoading: Boolean = true,
    var isError: String = "",
    var data: Flow<PagingData<PokemonResult>> = emptyFlow()
)

@HiltViewModel
class PokemonListViewModel @Inject constructor(private val pokemonRepository: PokemonRepository) :
    ViewModel() {

    private val _pokemonUiState: MutableState<PokemonUIState> = mutableStateOf(PokemonUIState())
    val pokemonUIState: MutableState<PokemonUIState> = _pokemonUiState

    init {
        getPokemon()
    }

    private fun getPokemon() {
        viewModelScope.launch {

            when (val response = pokemonRepository.getPokemonList()) {
                is Resource.Error -> {
                    _pokemonUiState.value.isLoading = false
                    _pokemonUiState.value.isError = response.message.toString()
                }

                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    _pokemonUiState.value.isLoading = false
                    _pokemonUiState.value.data = response.data!!
                }
            }
        }
    }

    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)
        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }
}

