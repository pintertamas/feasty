package hu.bme.aut.feasty.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Ingredient(
    @SerializedName("food")
    val name: String,
    val image: String,
    val quantity: Double,
    val measure: String,
) : Serializable