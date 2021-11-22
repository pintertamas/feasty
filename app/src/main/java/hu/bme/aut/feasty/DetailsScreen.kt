package hu.bme.aut.feasty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import com.squareup.picasso.Picasso
import hu.bme.aut.feasty.databinding.ActivityDetailsBinding
import hu.bme.aut.feasty.model.Recipe
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.feasty.adapter.IngredientListAdapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

class DetailsScreen : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var ingredientListAdapter: IngredientListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        val recipe: Recipe = intent.getSerializableExtra("recipe") as Recipe

        binding.infoButton.setOnClickListener {
            goToInfo()
        }

        binding.shareButton.setOnClickListener {
            share(recipe.url)
        }

        binding.title.text = recipe.title
        Picasso.get().load(recipe.imageUri).into(binding.recipeImageCard)

        ("Ingredients").also { binding.ingredientsTitle.text = it }

        yesOrNoImage(isVegetarian(recipe.healthLabels), binding.vegetarianImage)
        yesOrNoImage(isVegan(recipe.healthLabels), binding.veganImage)
        yesOrNoImage(isGlutenFree(recipe.healthLabels), binding.glutenImage)
        yesOrNoImage(isDairyFree(recipe.healthLabels), binding.dairyImage)

        ingredientListAdapter.setData(recipe.ingredients)
    }

    private fun isVegetarian(healthLabels: List<String>): Boolean {
        if (healthLabels.contains("Pork-Free") && healthLabels.contains("Red-Meat-Free"))
            return true
        return false
    }

    private fun isVegan(healthLabels: List<String>): Boolean {
        if (healthLabels.contains("Pork-Free") && healthLabels.contains("Red-Meat-Free") &&
            healthLabels.contains("Egg-Free") && healthLabels.contains("Fish-Free") &&
            healthLabels.contains("Shellfish-Free") && healthLabels.contains("Crustacean-Free") &&
            healthLabels.contains("Mollusk-Free")
        )
            return true
        return false
    }

    private fun isGlutenFree(healthLabels: List<String>): Boolean {
        if (healthLabels.contains("Gluten-Free"))
            return true
        return false
    }

    private fun isDairyFree(healthLabels: List<String>): Boolean {
        if (healthLabels.contains("Dairy-Free"))
            return true
        return false
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

    private fun goToInfo() {
        binding.scrollView.scrollTo(0, binding.scrollView.bottom + 250)
    }

    private fun share(url: String) {
        val clipboard: ClipboardManager =
            getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("url", url)
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