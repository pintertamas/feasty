package hu.bme.aut.feasty.api

import hu.bme.aut.feasty.model.RecipeList
import hu.bme.aut.feasty.utils.Constants.Companion.APP_ID
import hu.bme.aut.feasty.utils.Constants.Companion.RAPID_API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RecipeListAPI {

    @GET("api/recipes/v2?type=public&app_id=$APP_ID&app_key=$RAPID_API_KEY")
    suspend fun getRecipes(@Query("q") query: String?): Response<RecipeList>
}