package hu.bme.aut.feasty.api

import hu.bme.aut.feasty.model.RecipeDetails
import hu.bme.aut.feasty.model.RecipeList
import hu.bme.aut.feasty.utils.Constants.Companion.NUMBER_OF_RESULTS
import hu.bme.aut.feasty.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeListAPI {
    @GET("/recipes/complexSearch?&number=$NUMBER_OF_RESULTS&offset=0&apiKey=$API_KEY&minCalories=0&minProtein=0&minCarbs=0&minFat=0")
    @Headers(
        "x-rapidapi-host: spoonacular-recipe-food-nutrition-v1.p.rapidapi.com",
        "x-rapidapi-key: $API_KEY"
    )
    suspend fun getRecipes(@Query("query") query: String?): Response<RecipeList>

    @GET("/recipes/{id}/information?includeNutrition=false&apiKey=$API_KEY")
    @Headers(
        "x-rapidapi-host: spoonacular-recipe-food-nutrition-v1.p.rapidapi.com",
        "x-rapidapi-key: $API_KEY"
    )
    suspend fun getRecipeDetailsByRecipeID(@Path("id") id: Long): Response<RecipeDetails>
}