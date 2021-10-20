package hu.bme.aut.feasty.model

import com.google.gson.annotations.SerializedName

data class RecipeList(
    @SerializedName("results")
    val recipes: List<Recipe>,
    val totalResults: Int
)
