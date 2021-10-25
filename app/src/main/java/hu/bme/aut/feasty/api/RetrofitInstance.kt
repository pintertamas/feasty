package hu.bme.aut.feasty.api

import hu.bme.aut.feasty.utils.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


object RetrofitInstance {

    private val retrofit by lazy {
        val client: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS).build()
        Retrofit.Builder()
            .baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: RecipeListAPI by lazy {
        retrofit.create(RecipeListAPI::class.java)
    }
}