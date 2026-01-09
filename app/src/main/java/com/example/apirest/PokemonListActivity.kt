package com.example.apirest

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.apirest.model.PokemonResponse
import com.example.apirest.network.ApiService
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class PokemonListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_list)

        val listView = findViewById<ListView>(R.id.listView)
        val profileBtn = findViewById<Button>(R.id.btnProfile)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)

        api.getPokemons().enqueue(object : Callback<PokemonResponse> {
            override fun onResponse(
                call: Call<PokemonResponse>,
                response: Response<PokemonResponse>
            ) {
                val pokemons = response.body()?.results ?: emptyList()
                val names = pokemons.map { it.name }

                listView.adapter = ArrayAdapter(
                    this@PokemonListActivity,
                    android.R.layout.simple_list_item_1,
                    names
                )

                // 👉 CLICK NO POKÉMON
                listView.setOnItemClickListener { _, _, position, _ ->
                    val intent = Intent(
                        this@PokemonListActivity,
                        PokemonDetailActivity::class.java
                    )
                    intent.putExtra("pokemon_name", names[position])
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {}
        })

        profileBtn.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}
