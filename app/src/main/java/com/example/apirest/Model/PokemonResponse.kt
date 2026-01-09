package com.example.apirest.model

data class PokemonResponse(
    val results: List<Pokemon>
)

data class Pokemon(
    val name: String
)
