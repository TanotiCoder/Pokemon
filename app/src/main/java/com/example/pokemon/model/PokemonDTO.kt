package com.example.pokemon.model

data class PokemonDTO(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonResult>
)