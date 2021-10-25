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
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.feasty.adapter.IngredientListAdapter

class DetailsScreen : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var ingredientListAdapter: IngredientListAdapter

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

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
        ("Ingredients for " + recipeDetails.servings + " servings").also {
            binding.ingredientsTitle.text = it
        }

        ingredientListAdapter.setData(recipeDetails.ingredients)
    }

    private fun hideIconAndText(minutes: Int, text: TextView, image: ImageView) {
        if (minutes == 0) {
            image.visibility = View.GONE
            text.visibility = View.GONE
        } else {
            ("$minutes mins").also { text.text = it }
        }
    }

    private fun setupRecyclerView() {
        ingredientListAdapter = IngredientListAdapter()
        binding.ingredientList.adapter = ingredientListAdapter
        binding.ingredientList.layoutManager = LinearLayoutManager(this)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish()
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}