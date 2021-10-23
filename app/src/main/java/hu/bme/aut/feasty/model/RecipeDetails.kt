package hu.bme.aut.feasty.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RecipeDetails(
    var id: Long,
    var title: String,
    @SerializedName("extendedIngredients")
    var ingredients: MutableList<Ingredient>,
    var image: String,
    var preparationMinutes: Int,
    var cookingMinutes: Int,
    var instructions: String,
    var servings: Int,
    var vegetarian: Boolean,
    var vegan: Boolean,
    var glutenFree: Boolean,
    var dairyFree: Boolean,
    var veryHealthy: Boolean,
    var cheap: Boolean
) : Serializable