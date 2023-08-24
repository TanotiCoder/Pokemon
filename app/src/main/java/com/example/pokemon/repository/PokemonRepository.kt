package com.example.pokemon.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pokemon.model.PokemonResult
import com.example.pokemon.model.pokemon_detail.PokemonDetail
import com.example.pokemon.service.ApiService
import com.example.pokemon.source.PokemonSource
import com.example.pokemon.utils.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import javax.inject.Inject

class PokemonRepository @Inject constructor(private val apiService: ApiService) {
    fun getPokemonList(): Resource<Flow<PagingData<PokemonResult>>> {
        return try {
            Resource.Success(
                Pager(
                    config = PagingConfig(pageSize = 10, enablePlaceholders = true),
                    pagingSourceFactory = { PokemonSource(apiService = apiService) }
                ).flow
            )
        } catch (e: Exception) {
            Resource.Error("Something went wrong")
        } catch (e: HttpException) {
            Resource.Error("Please check your Connection")
        }
    }

    suspend fun getPokemonDetail(name: String): Resource<PokemonDetail> {
        return try {
            Resource.Success(apiService.getPokemonDetail(name))
        } catch (e: Exception) {
            Resource.Error("Something went wrong")
        } catch (e: HttpException) {
            Resource.Error("Please check your Connection")
        }
    }
}

