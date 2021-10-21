package hu.bme.aut.feasty.model

import com.google.gson.annotations.SerializedName

data class RecipeList(
    @SerializedName("results")
    val recipes: MutableList<Recipe>,
    var baseUri: String,
    var totalResults: Int
)
