package com.example.pokemon.service

import com.example.pokemon.model.PokemonDTO
import com.example.pokemon.model.pokemon_detail.PokemonDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

///https://pokeapi.co/api/v2/pokemon?offset=0&limit=20
interface ApiService {
    @GET("pokemon?limit=10")
    suspend fun getPokemonList(@Query("offset") offset: Int): PokemonDTO

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(@Path("name") name: String):PokemonDetail
}