package com.example.pokemon

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.pokemon.model.PokemonResult
import com.example.pokemon.ui.Chart
import com.example.pokemon.ui.HomeScreen
import com.example.pokemon.ui.LottieCompositionAnimation
import com.example.pokemon.ui.NavController
import com.example.pokemon.ui.PokemonCard
import com.example.pokemon.ui.theme.PokemonTheme
import com.example.pokemon.viewmodel.PokemonListViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokemonTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    NavController(this)
                    //Check()
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Check() {
    var showChart by remember {
        mutableStateOf(true)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(
                600.dp
            )
    ) {
        Chart(
            data = mapOf(
                Pair("Jan", 6f),
                Pair("Feb", 0.25f),
                Pair("Mar", 9f),
                Pair("Apr", 7f),
                Pair("May", 8f),
                Pair("Jun", 9f),
                Pair("Jul", 3f),
                Pair("Aug", 11f),
                Pair("Sep", 15f),
            ), height = 250.dp,
            isExpanded = showChart,
            bottomEndRadius = 30.dp,
            bottomStartRadius = 30.dp
        ) {
            showChart = !showChart
        }
    }
}

@HiltAndroidApp
class Pokemon() : Application()