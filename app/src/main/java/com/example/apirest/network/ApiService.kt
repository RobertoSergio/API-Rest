package com.example.apirest.network

import com.example.apirest.model.PokemonDetailResponse
import com.example.apirest.model.PokemonResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("api/v2/pokemon")
    fun getPokemons(
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int = 0
    ): Call<PokemonResponse>

    @GET("api/v2/pokemon/{name}")
    fun getPokemonDetail(
        @Path("name") name: String
    ): Call<PokemonDetailResponse>
}