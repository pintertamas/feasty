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
import android.widget.ImageView
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


        hideIconAndText(
            recipeDetails.preparationMinutes,
            binding.preparationTime,
            binding.preparationImage
        )
        hideIconAndText(recipeDetails.cookingMinutes, binding.cookingTime, binding.cookingImage)
        hideIconAndText(recipeDetails.readyInMinutes, binding.readyInMinutes, binding.readyInImage)

        if (recipeDetails.instructions.startsWith("Instructions")) recipeDetails.instructions.replaceFirst(
            "Instructions",
            ""
        )
        (recipeDetails.instructions + " mins").also { binding.instructionsText.text = it }
        ("Ingredients for " + recipeDetails.servings + " servings\n").also {
            binding.ingredientsTitle.text = it
        }

        val linearLayout: LinearLayout = binding.ingredientList
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater

        recipeDetails.ingredients.forEach { _ ->
            val newIngredientView: View = inflater.inflate(R.layout.ingredient_item, null)
            linearLayout.addView(newIngredientView, linearLayout.childCount - 1)
        }
    }

    private fun hideIconAndText(minutes: Int, text: TextView, image: ImageView) {
        if (minutes == 0) {
            image.visibility = View.GONE
            text.visibility = View.GONE
        } else {
            ("$minutes mins").also { text.text = it }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            System.out.println(this)
            finish()
        }
        return super.onKeyDown(keyCode, event)
    }
}