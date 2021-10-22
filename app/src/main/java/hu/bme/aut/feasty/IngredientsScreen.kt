package hu.bme.aut.feasty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.bme.aut.feasty.databinding.ActivityIngredientsBinding

class IngredientsScreen : AppCompatActivity() {
    private lateinit var binding: ActivityIngredientsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIngredientsBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}