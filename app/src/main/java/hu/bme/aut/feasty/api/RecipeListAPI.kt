package hu.bme.aut.feasty.api

import hu.bme.aut.feasty.model.RecipeList
import hu.bme.aut.feasty.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RecipeListAPI {

    @GET("/recipes/search?&number=" + Constants.NUMBER_OF_RESULTS + "&offset=0")
    @Headers("x-rapidapi-host: spoonacular-recipe-food-nutrition-v1.p.rapidapi.com", "x-rapidapi-key: 061bd4ab6dmsh6078975ea7b1babp197554jsnc249ce8ca8a7")
    suspend fun getRecipes(@Query("query") query: String?): Response<RecipeList>

}