package hu.bme.aut.feasty

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bme.aut.feasty.model.RecipeList
import hu.bme.aut.feasty.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class RecipeListViewModel(private val repository: Repository) : ViewModel() {

    val myResponse: MutableLiveData<Response<RecipeList>> = MutableLiveData()

    fun getRecipeList(query: String) {
        viewModelScope.launch {
            val response: Response<RecipeList> = repository.getRecipeList(query)
            myResponse.value = response
        }
    }
}