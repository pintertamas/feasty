package hu.bme.aut.feasty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import hu.bme.aut.feasty.repository.Repository

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: RecipeListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = Repository()
        val viewModelFactory = RecipeListViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(RecipeListViewModel::class.java)
        viewModel.getRecipeList("waffle")
        viewModel.myResponse.observe(this, Observer {response->
            if (response.isSuccessful) {
                Log.d("Response", response.body()?.recipes?.size.toString())
                Log.d("Response", response.body()?.totalResults.toString())
                if (response.body()?.recipes?.isNotEmpty() == true)
                    Log.d("Response", response.body()?.recipes!![0].toString())
            } else {
                Log.d("Response", response.errorBody().toString())
            }
        })
    }
}