package hu.bme.aut.feasty.repository

import hu.bme.aut.feasty.api.RetrofitInstance
import hu.bme.aut.feasty.model.RecipeDetails
import hu.bme.aut.feasty.model.RecipeList
import retrofit2.Response

class Repository {

    suspend fun getRecipeList(query: String): Response<RecipeList> {
        return RetrofitInstance.api.getRecipes(query)
    }

    suspend fun getRecipeDetailsByRecipeID(recipeID: Long): Response<RecipeDetails> {
        return RetrofitInstance.api.getRecipeDetailsByRecipeID(recipeID)
    }
}