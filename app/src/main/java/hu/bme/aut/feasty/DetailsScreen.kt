package hu.bme.aut.feasty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import com.squareup.picasso.Picasso
import hu.bme.aut.feasty.databinding.ActivityDetailsBinding
import hu.bme.aut.feasty.model.RecipeDetails
import hu.bme.aut.feasty.model.Recipe

class DetailsScreen : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        System.out.println(this)

        val recipe: Recipe = intent.getSerializableExtra("recipe") as Recipe
        val recipeDetails: RecipeDetails = (intent.getSerializableExtra("details") as RecipeDetails)

        binding.title.text = recipeDetails.title
        ("ready in " + recipeDetails.readyInMinutes.toString() + " minutes").also {
            binding.readyInText.text = it
        }
        val imageURL = "https://spoonacular.com/recipeImages/" + recipe.imageUri
        Picasso.get().load(imageURL).into(binding.recipeImageCard)

        //binding.textView3.text = recipeDetails.ingredients[0].measures.metric.amount.toString()
        binding.preparationTimeText.text = recipeDetails.preparationMinutes.toString()
        binding.cookingTimeText.text = recipeDetails.cookingMinutes.toString()
    }

    override fun onStop() {
        super.onStop()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        finish()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            System.out.println(this)
            finish()
        }
        return super.onKeyDown(keyCode, event)
    }
}