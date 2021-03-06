package hu.bme.aut.feasty.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RecipeDetails(
    var id: Long,
    var image: String,
    var title: String,
    @SerializedName("sourceUrl")
    var url: String,
    @SerializedName("extendedIngredients")
    var ingredients: MutableList<Ingredient>,
    var instructions: String?,
    var preparationMinutes: Int,
    var cookingMinutes: Int,
    var readyInMinutes: Int,
    var servings: Int,
    var vegetarian: Boolean,
    var vegan: Boolean,
    var glutenFree: Boolean,
    var dairyFree: Boolean,
) : Serializable