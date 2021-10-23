package hu.bme.aut.feasty

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.feasty.adapter.RecipeListAdapter
import hu.bme.aut.feasty.databinding.ActivityMainBinding
import hu.bme.aut.feasty.model.Recipe
import hu.bme.aut.feasty.repository.Repository
import hu.bme.aut.feasty.viewmodel.RecipeListViewModel
import hu.bme.aut.feasty.viewmodel.RecipeListViewModelFactory

class MainActivity : AppCompatActivity(), RecipeListAdapter.RecipeItemClickListener,
    RecipeListAdapter.RecyclerViewUpdatesListener {

    private lateinit var viewModel: RecipeListViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var recipeListAdapter: RecipeListAdapter
    private var recipeListState: Parcelable? = null

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

                viewModel.recipeListResponse.observe(this, { response ->
                    if (response.isSuccessful) {
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

    override fun onResume() {
        super.onResume();

        if (recipeListState != null) {
            binding.recyclerView.layoutManager?.onRestoreInstanceState(recipeListState);
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
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun setupRecyclerView() {
        recipeListAdapter = RecipeListAdapter(this, this)
        binding.recyclerView.adapter = recipeListAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onRecipeClicked(recipe: Recipe) {
        viewModel.getIngredients(recipe.recipeId)

        viewModel.ingredientsResponse.observe(this, { response ->
            if (response.isSuccessful) {
                response.body()?.let {
                    runOnUiThread {
                        val detailsIntent = Intent(this, DetailsScreen::class.java).apply {
                            putExtra("recipe", recipe)
                            putExtra("details", it)
                        }
                        startActivity(detailsIntent)
                    }
                }
            } else {
                Toast.makeText(this, response.code(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onSaveInstanceState(state: Bundle) {
        super.onSaveInstanceState(state)

        recipeListState = binding.recyclerView.layoutManager?.onSaveInstanceState()
        state.putParcelable(RECIPE_LIST_STATE_KEY, recipeListState)
    }

    override fun onRestoreInstanceState(state: Bundle) {
        super.onRestoreInstanceState(state);

        recipeListState = state.getParcelable(RECIPE_LIST_STATE_KEY);
    }

    override fun onRecyclerViewChanged(itemCount: Int) {
        if (itemCount >= 0) {
            binding.recyclerView.visibility = View.VISIBLE
            binding.placeholderImage.visibility = View.GONE
        } else {
            binding.recyclerView.visibility = View.GONE
            binding.placeholderImage.visibility = View.VISIBLE
        }
    }

    companion object {
        const val RECIPE_LIST_STATE_KEY: String = "recipeListState"
    }
}