package hu.bme.aut.feasty.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bme.aut.feasty.model.RecipeDetails
import hu.bme.aut.feasty.model.RecipeList
import hu.bme.aut.feasty.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class RecipeListViewModel(private val repository: Repository) : ViewModel() {

    var recipeListResponse: MutableLiveData<Response<RecipeList>> = MutableLiveData()

    fun getRecipeList(query: String) {
        recipeListResponse = MutableLiveData()
        viewModelScope.launch {
            val response: Response<RecipeList> = repository.getRecipeList(query)
            System.out.println(response)
            recipeListResponse.value = response
        }
    }
}