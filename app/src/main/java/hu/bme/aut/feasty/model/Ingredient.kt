package hu.bme.aut.feasty.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Ingredient(
    val id: Long,
    @SerializedName("nameClean")
    val name: String,
    val image: String,
    val measures: Measures
) : Serializable

data class Measures(
    val metric: MetricMeasures
) : Serializable

data class MetricMeasures(
    val amount: Double,
    val unitShort: String
) : Serializable