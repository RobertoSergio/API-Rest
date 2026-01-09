package com.example.apirest

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.apirest.model.PokemonResponse
import com.example.apirest.network.ApiService
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale

class PokemonListActivity : AppCompatActivity() {

    private var offset = 0
    private val limit = 100
    private var isLoading = false
    private var hasMore = true
    private val pokemonList = mutableListOf<String>()
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_list)

        val listView = findViewById<ListView>(R.id.listView)
        val profileBtn = findViewById<Button>(R.id.btnProfile)

        adapter = ArrayAdapter(this, R.layout.item_pokemon, R.id.textView, pokemonList)
        listView.adapter = adapter

        progressBar = ProgressBar(this)
        progressBar.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        progressBar.visibility = View.GONE
        (listView.parent as? ViewGroup)?.addView(progressBar)

        loadPokemons()

        listView.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScroll(
                view: AbsListView?,
                firstVisibleItem: Int,
                visibleItemCount: Int,
                totalItemCount: Int
            ) {
                if (firstVisibleItem + visibleItemCount >= totalItemCount &&
                    totalItemCount > 0 &&
                    !isLoading &&
                    hasMore) {
                    loadPokemons()
                }
            }

            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {}
        })

        listView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, PokemonDetailActivity::class.java)
            intent.putExtra("pokemon_name", pokemonList[position].lowercase(Locale.ROOT))
            startActivity(intent)
        }

        profileBtn.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    private fun loadPokemons() {
        if (isLoading) return

        isLoading = true
        progressBar.visibility = View.VISIBLE

        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)

        api.getPokemons(limit, offset).enqueue(object : Callback<PokemonResponse> {
            override fun onResponse(
                call: Call<PokemonResponse>,
                response: Response<PokemonResponse>
            ) {
                progressBar.visibility = View.GONE
                isLoading = false

                if (response.isSuccessful) {
                    val pokemons = response.body()?.results ?: emptyList()

                    if (pokemons.isNotEmpty()) {
                        val newNames = pokemons.map {
                            it.name.replaceFirstChar { char ->
                                char.uppercaseChar()
                            }
                        }
                        pokemonList.addAll(newNames)
                        adapter.notifyDataSetChanged()

                        offset += limit
                        hasMore = pokemons.size >= limit
                    } else {
                        hasMore = false
                    }
                } else {
                    hasMore = false
                }
            }

            override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {
                progressBar.visibility = View.GONE
                isLoading = false
                hasMore = false
            }
        })
    }
}