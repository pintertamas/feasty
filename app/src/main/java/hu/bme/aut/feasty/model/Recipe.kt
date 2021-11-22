package hu.bme.aut.feasty.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RecipeList(
    @SerializedName("hits")
    val records: MutableList<RecipeRecord>,
)

data class RecipeRecord(
    val recipe: Recipe,
) : Serializable

data class Recipe(
    @SerializedName("label")
    val title: String,
    @SerializedName("totalTime")
    val readyInMinutes: Int,
    val healthLabels: List<String>,
    @SerializedName("image")
    val imageUri: String,
    @SerializedName("url")
    val url: String,
    val ingredients: MutableList<Ingredient>,
) : Serializable