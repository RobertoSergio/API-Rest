package com.example.apirest.network

import com.example.apirest.model.PokemonDetailResponse
import com.example.apirest.model.PokemonResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("api/v2/pokemon")
    fun getPokemons(): Call<PokemonResponse>

    @GET("api/v2/pokemon/{name}")
    fun getPokemonDetail(
        @Path("name") name: String
    ): Call<PokemonDetailResponse>
}
