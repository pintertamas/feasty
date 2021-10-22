package hu.bme.aut.feasty

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.feasty.adapter.RecipeListAdapter
import hu.bme.aut.feasty.databinding.ActivityMainBinding
import hu.bme.aut.feasty.model.Recipe
import hu.bme.aut.feasty.repository.Repository

class MainActivity : AppCompatActivity(), RecipeListAdapter.RecipeItemClickListener {

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

                this.handleSubmit()
            }
            true
        }

        binding.searchBar.setOnClickListener {
            binding.searchBar.isCursorVisible = true
        }
    }

    private fun handleSubmit() {
        hideKeyboard()
        binding.searchBar.setText("")
        binding.searchBar.clearFocus()
        binding.searchBar.isCursorVisible = false
    }

    private fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun setupRecyclerView() {
        recipeListAdapter = RecipeListAdapter(this)
        binding.recyclerView.adapter = recipeListAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onRecipeClicked(recipe: Recipe) {
        runOnUiThread {
            //TODO: menjen át a következő oldalra id alapján
            System.out.println("recipe id: " + recipe.recipeId)
        }
    }
}