package com.example.pokemon.model.pokemon_detail

data class PokemonDetail(
    val height: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val types: List<TypeX>,
    val weight: Int
)