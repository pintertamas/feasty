package hu.bme.aut.feasty.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Recipe(
    @SerializedName("id")
    val recipeId: Long,
    val title: String,
    @SerializedName("image")
    val imageUri: String,
    val nutrition: Nutrition,
) : Serializable

data class Nutrition(
    val nutrients: MutableList<Nutrient>,
) : Serializable

data class Nutrient(
    val title: String,
    val name: String,
    val amount: Double,
    val unit: String,
) : Serializable
