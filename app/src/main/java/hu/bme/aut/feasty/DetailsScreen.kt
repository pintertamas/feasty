package hu.bme.aut.feasty

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
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.feasty.adapter.IngredientListAdapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.text.Html

class DetailsScreen : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var ingredientListAdapter: IngredientListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        val recipe: Recipe = intent.getSerializableExtra("recipe") as Recipe
        val recipeDetails: RecipeDetails = (intent.getSerializableExtra("details") as RecipeDetails)

        binding.infoButton.setOnClickListener {
            goToInfo()
        }

        binding.shareButton.setOnClickListener {
            share(recipeDetails)
        }

        binding.title.text = recipeDetails.title
        val imageURL = recipe.imageUri
        Picasso.get().load(imageURL).into(binding.recipeImageCard)

        hideIconAndText(
            recipeDetails.preparationMinutes,
            binding.preparationTime,
            binding.preparationImage
        )
        hideIconAndText(recipeDetails.cookingMinutes, binding.cookingTime, binding.cookingImage)
        hideIconAndText(recipeDetails.readyInMinutes, binding.readyInMinutes, binding.readyInImage)

        if (!recipeDetails.instructions.equals(null)) {
            if (recipeDetails.instructions!!.startsWith("Instructions"))
                recipeDetails.instructions = recipeDetails.instructions!!.replaceFirst(
                    "Instructions",
                    ""
                ).trim()

            recipeDetails.instructions = removeWhitespaces(recipeDetails.instructions!!)
        }

        (recipeDetails.instructions).also { binding.instructionsText.text = it }
        ("Ingredients for " + recipeDetails.servings + " servings").also {
            binding.ingredientsTitle.text = it
        }

        binding.instructionsText.text =
            Html.fromHtml(recipeDetails.instructions, Html.FROM_HTML_MODE_COMPACT)


        yesOrNoImage(recipeDetails.vegetarian, binding.vegetarianImage)
        yesOrNoImage(recipeDetails.vegan, binding.veganImage)
        yesOrNoImage(recipeDetails.glutenFree, binding.glutenImage)
        yesOrNoImage(recipeDetails.dairyFree, binding.dairyImage)

        ingredientListAdapter.setData(recipeDetails.ingredients)
    }

    private fun removeWhitespaces(s: String): String {
        var res = s.replace("\t", " ")
        res = res.replace(".", ". ")
        while (res.contains("  ")) {
            res = res.replace("  ", " ")
        }
        while (res.contains("\n\n")) {
            res = res.replace("\n\n", "\n")
        }
        return res
    }

    private fun yesOrNoImage(info: Boolean, image: ImageView) {
        if (info)
            image.setImageResource(R.drawable.yes)
        else image.setImageResource(R.drawable.no)
    }

    private fun hideIconAndText(minutes: Int, text: TextView, image: ImageView) {
        if (minutes == 0) {
            image.visibility = View.GONE
            text.visibility = View.GONE
        } else {
            ("$minutes mins").also { text.text = it }
        }
    }

    private fun goToInfo() {
        binding.scrollView.scrollTo(0, binding.scrollView.bottom + 10000)
    }

    private fun share(recipeDetails: RecipeDetails) {
        val clipboard: ClipboardManager =
            getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("url", recipeDetails.url)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, "Link copied to clipboard", Toast.LENGTH_SHORT).show()
    }

    private fun setupRecyclerView() {
        ingredientListAdapter = IngredientListAdapter()
        binding.ingredientList.adapter = ingredientListAdapter
        binding.ingredientList.layoutManager = LinearLayoutManager(this)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(parentActivityIntent)
            finish()
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}