package com.example.pokemon.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokemon.viewmodel.PokemonListViewModel

@Composable
fun NavController(
    context: Context,
    pokemonListViewModel: PokemonListViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                pokemonListViewModel = pokemonListViewModel,
                context = context,
                goToDetail = { bgColor, name -> navController.navigate("detail/$bgColor/$name") })
        }
        composable(
            "detail/{bgColor}/{name}",
            arguments = listOf(navArgument("bgColor") { type = NavType.StringType })
        ) { backStackEntry ->
            val dominantColor = remember {
                val color = backStackEntry.arguments?.getString("bgColor")
                color?.let { Color(it.toInt()) } ?: Color.White
            }
            PokemonDetail(dominantColor)
        }
    }
}
