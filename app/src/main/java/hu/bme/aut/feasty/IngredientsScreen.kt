package hu.bme.aut.feasty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import hu.bme.aut.feasty.databinding.ActivityIngredientsBinding
import hu.bme.aut.feasty.model.Ingredient
import hu.bme.aut.feasty.model.RecipeDetails
import hu.bme.aut.feasty.model.Recipe

class IngredientsScreen : AppCompatActivity() {
    private lateinit var binding: ActivityIngredientsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIngredientsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recipe : Recipe = intent.getSerializableExtra("recipe") as Recipe
        //val recipeDetails : RecipeDetails = (intent.getSerializableExtra("details") as RecipeDetails)

        binding.title.text = recipe.title
        //("ready in " + recipe.readyInMinutes.toString() + " minutes").also { binding.readyInMinutes.text = it }
        val imageURL = "https://spoonacular.com/recipeImages/" + recipe.imageUri
        Picasso.get().load(imageURL).into(binding.recipeImageCard)

        //binding.textView3.text = recipeDetails.ingredients[0].measures.metric.amount.toString()
        //binding.textView2.text = recipeDetails.instructions
    }
}