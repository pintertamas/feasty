package hu.bme.aut.feasty.repository

import hu.bme.aut.feasty.api.RetrofitInstance
import hu.bme.aut.feasty.model.IngredientList
import hu.bme.aut.feasty.model.Ingredients
import hu.bme.aut.feasty.model.RecipeList
import retrofit2.Response

class Repository {

    suspend fun getRecipeList(query: String): Response<RecipeList> {
        return RetrofitInstance.api.getRecipes(query)
    }

    suspend fun getIngredientsByRecipeID(recipeID: Long): Response<IngredientList> {
        return RetrofitInstance.api.getIngredientsByRecipeID(recipeID)
    }
}