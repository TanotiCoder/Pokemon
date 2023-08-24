package com.example.pokemon.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon.model.pokemon_detail.PokemonDetail
import com.example.pokemon.repository.PokemonRepository
import com.example.pokemon.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


data class PokemonDetailUIState(
     var isError: String = "",
    var data: PokemonDetail? = null
)

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _pokemonDetail: MutableState<PokemonDetail?> = mutableStateOf(null)
    val pokemonDetail: MutableState<PokemonDetail?> = _pokemonDetail
    private val name: String = checkNotNull(savedStateHandle["name"])

    init {
        getPokemonDetail(name)
    }

    private fun getPokemonDetail(name: String) {
        viewModelScope.launch {
             when (val response = pokemonRepository.getPokemonDetail(name)) {
                is Resource.Error -> {
                    //_pokemonDetail.value.isError = response.message!!
                }

                is Resource.Loading -> {
                 }

                is Resource.Success -> {
                    _pokemonDetail.value = response.data!!
                }
            }
        }
    }
}