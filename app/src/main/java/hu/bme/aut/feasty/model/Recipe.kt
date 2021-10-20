package hu.bme.aut.feasty.model

import com.google.gson.annotations.SerializedName

data class Recipe(
    @SerializedName("id")
    val recipeId: Long,
    val title: String,
    val readyInMinutes: Int,
    @SerializedName("image")
    val imageUri: String
)