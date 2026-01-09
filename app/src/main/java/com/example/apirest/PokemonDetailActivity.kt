package com.example.apirest

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.apirest.model.PokemonDetailResponse
import com.example.apirest.network.ApiService
import com.squareup.picasso.Picasso
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class PokemonDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_detail)

        val nameText = findViewById<TextView>(R.id.txtName)
        val heightText = findViewById<TextView>(R.id.txtHeight)
        val weightText = findViewById<TextView>(R.id.txtWeight)
        val typeText = findViewById<TextView>(R.id.txtType)
        val image = findViewById<ImageView>(R.id.imgPokemon)

        val pokemonName = intent.getStringExtra("pokemon_name")

        if (pokemonName.isNullOrEmpty()) {
            Toast.makeText(this, "Erro: Nome do Pokémon não encontrado", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        nameText.text = "Carregando..."
        heightText.text = ""
        weightText.text = ""
        typeText.text = ""

        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)

        api.getPokemonDetail(pokemonName).enqueue(object : Callback<PokemonDetailResponse> {
            override fun onResponse(
                call: Call<PokemonDetailResponse>,
                response: Response<PokemonDetailResponse>
            ) {
                if (response.isSuccessful) {
                    val pokemon = response.body()
                    if (pokemon != null) {
                        nameText.text = pokemon.name.uppercase()
                        heightText.text = getString(R.string.height, pokemon.height)
                        weightText.text = getString(R.string.weight, pokemon.weight)

                        val types = pokemon.types.joinToString(", ") {
                            it.type.name
                        }
                        typeText.text = getString(R.string.type, types)

                        if (pokemon.sprites.front_default.isNotEmpty()) {
                            Picasso.get()
                                .load(pokemon.sprites.front_default)
                                .into(image)
                        }
                    } else {
                        showError("Dados do Pokémon não disponíveis")
                    }
                } else {
                    when (response.code()) {
                        404 -> showError("Pokémon não encontrado")
                        500 -> showError("Erro no servidor")
                        else -> showError("Erro ${response.code()}")
                    }
                }
            }

            override fun onFailure(call: Call<PokemonDetailResponse>, t: Throwable) {
                when (t) {
                    is java.net.UnknownHostException -> showError("Sem conexão com a internet")
                    is java.net.SocketTimeoutException -> showError("Tempo limite excedido")
                    else -> showError("Erro na conexão")
                }
            }
        })
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        findViewById<TextView>(R.id.txtName).text = "Erro ao carregar"
    }
}