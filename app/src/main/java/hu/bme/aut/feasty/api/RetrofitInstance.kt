package hu.bme.aut.feasty.api

import hu.bme.aut.feasty.utils.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: RecipeListAPI by lazy {
        retrofit.create(RecipeListAPI::class.java)
    }
}