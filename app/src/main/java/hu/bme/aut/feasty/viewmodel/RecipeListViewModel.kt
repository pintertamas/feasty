package hu.bme.aut.feasty.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bme.aut.feasty.model.IngredientList
import hu.bme.aut.feasty.model.Ingredients
import hu.bme.aut.feasty.model.RecipeList
import hu.bme.aut.feasty.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class RecipeListViewModel(private val repository: Repository) : ViewModel() {

    val recipeListResponse: MutableLiveData<Response<RecipeList>> = MutableLiveData()

    fun getRecipeList(query: String) {
        viewModelScope.launch {
            val response: Response<RecipeList> = repository.getRecipeList(query)
            recipeListResponse.value = response
        }
    }

    val ingredientsResponse: MutableLiveData<Response<IngredientList>> = MutableLiveData()

    fun getIngredients(recipeID: Long) {
        viewModelScope.launch {
            val response: Response<IngredientList> = repository.getIngredientsByRecipeID(recipeID)
            ingredientsResponse.value = response
        }
    }
}