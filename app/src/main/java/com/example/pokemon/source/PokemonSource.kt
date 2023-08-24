package com.example.pokemon.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokemon.model.PokemonResult
import com.example.pokemon.service.ApiService
import retrofit2.HttpException
import java.io.IOException

class PokemonSource(private val apiService: ApiService) : PagingSource<Int, PokemonResult>() {
    override fun getRefreshKey(state: PagingState<Int, PokemonResult>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonResult> {
        return try {
            val nextKey = params.key ?: 1
            val data = apiService.getPokemonList(nextKey * 20)
            LoadResult.Page(
                prevKey = if (data.previous == null) null else nextKey - 1,
                nextKey = if (data.next == null) null else nextKey + 1,
                data = data.results
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}

