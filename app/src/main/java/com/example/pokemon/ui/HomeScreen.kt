package com.example.pokemon.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.pokemon.R
import com.example.pokemon.model.PokemonResult
import com.example.pokemon.viewmodel.PokemonListViewModel


@Composable
fun HomeScreen(
    goToDetail: (bgColor: String, name: String) -> Unit,
    context: Context,
    modifier: Modifier = Modifier,
    pokemonListViewModel: PokemonListViewModel
) {
    val data = pokemonListViewModel.pokemonUIState.value
    val pokeData: LazyPagingItems<PokemonResult> = data.data.collectAsLazyPagingItems()
    val defaultDominantColor = MaterialTheme.colorScheme.surface
    val dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }

//    if (data.isLoading) {
//        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//            LottieCompositionAnimation(modifier = Modifier, raw = R.raw.animation_pokemon)
//        }
//    }
    if (data.isError.isNotEmpty()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = "Error.....")

        }
    }

    when (pokeData.loadState.refresh) {
        is LoadState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                LottieCompositionAnimation(modifier = Modifier, raw = R.raw.animation_logo_pokemon)
            }
        }

        is LoadState.Error -> {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(text = "......Error.....")
            }
        }

        is LoadState.NotLoading -> {
            LazyColumn(modifier.fillMaxSize()) {
                items(pokeData.itemCount) {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .background(dominantColor),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        PokemonCard(
                            goToDetail = { bg, name -> goToDetail(bg, name) },
                            text = pokeData[it]!!.name,
                            url = pokeData[it]!!.url,
                            context = context
                        )
                    }
                }
            }
        }

        else -> {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(text = "......Else..... and ")
            }
        }
    }
}
