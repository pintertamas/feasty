package hu.bme.aut.feasty

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import com.squareup.picasso.Picasso
import hu.bme.aut.feasty.databinding.ActivityDetailsBinding
import hu.bme.aut.feasty.model.RecipeDetails
import hu.bme.aut.feasty.model.Recipe
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView

class DetailsScreen : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        System.out.println(this)

        val recipe: Recipe = intent.getSerializableExtra("recipe") as Recipe
        val recipeDetails: RecipeDetails = (intent.getSerializableExtra("details") as RecipeDetails)

        binding.title.text = recipeDetails.title
        val imageURL = "https://spoonacular.com/recipeImages/" + recipe.imageUri
        Picasso.get().load(imageURL).into(binding.recipeImageCard)

        //binding.textView3.text = recipeDetails.ingredients[0].measures.metric.amount.toString()
        (recipeDetails.preparationMinutes.toString() + " mins").also {
            binding.preparationTime.text = it
        }
        (recipeDetails.cookingMinutes.toString() + " mins").also { binding.cookingTime.text = it }
        (recipeDetails.readyInMinutes.toString() + " mins").also {
            binding.readyInMinutes.text = it
        }
        if (recipeDetails.instructions.startsWith("Instructions")) recipeDetails.instructions.replaceFirst(
            "Instructions",
            ""
        )
        (recipeDetails.instructions + " mins").also { binding.instructionsText.text = it }
        ("Ingredients for " + recipeDetails.servings + " servings\n").also { binding.ingredientsTitle.text = it }

        val linearLayout : LinearLayout = binding.ingredientList
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater

        recipeDetails.ingredients.forEach {
            val newIngredientView: View = inflater.inflate(R.layout.ingredient_item, null)
            linearLayout.addView(newIngredientView, linearLayout.getChildCount() - 1)
        }

        System.out.println(recipeDetails.ingredients.size)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            System.out.println(this)
            finish()
        }
        return super.onKeyDown(keyCode, event)
    }
}