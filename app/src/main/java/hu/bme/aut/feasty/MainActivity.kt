package hu.bme.aut.feasty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.feasty.adapter.RecipeListAdapter
import hu.bme.aut.feasty.databinding.ActivityMainBinding
import hu.bme.aut.feasty.repository.Repository

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: RecipeListViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var recipeListAdapter: RecipeListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        val repository = Repository()
        val viewModelFactory = RecipeListViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(RecipeListViewModel::class.java)

        binding.searchBar.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.getRecipeList(binding.searchBar.text.toString())

                viewModel.myResponse.observe(this, { response ->
                    if (response.isSuccessful) {
                        response.body()?.let { Log.d("Response ", it.toString()) }
                        response.body()?.let {
                            runOnUiThread {
                                recipeListAdapter.setData(it.recipes)
                            }
                        }
                    } else {
                        Toast.makeText(this, response.code(), Toast.LENGTH_SHORT).show()
                    }
                })

                binding.searchBar.setText("")
                binding.searchBar.clearFocus()
            }
            true
        }
    }

    private fun setupRecyclerView() {
        recipeListAdapter = RecipeListAdapter()
        binding.recyclerView.adapter = recipeListAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
}