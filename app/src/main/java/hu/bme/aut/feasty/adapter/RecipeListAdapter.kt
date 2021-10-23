package hu.bme.aut.feasty.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.feasty.model.Recipe
import com.squareup.picasso.Picasso
import hu.bme.aut.feasty.databinding.RecyclerViewItemBinding


class RecipeListAdapter(private val recipeItemClickedListener: RecipeItemClickListener) : RecyclerView.Adapter<RecipeListAdapter.RecipeListViewHolder>() {

    private var recipeList = mutableListOf<Recipe>()

    inner class RecipeListViewHolder(val binding: RecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RecipeListViewHolder(
        RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int {
        return this.recipeList.size
    }

    override fun onBindViewHolder(holder: RecipeListViewHolder, position: Int) {
        holder.binding.title.text = recipeList[position].title
        ("ready in " + recipeList[position].readyInMinutes.toString() + " minutes").also { holder.binding.readyInMinutes.text = it }
        val imageURL = "https://spoonacular.com/recipeImages/" + recipeList[position].imageUri
        Picasso.get().load(imageURL).into(holder.binding.recipeImageCard)

        holder.binding.containerCard.setOnClickListener {
            recipeItemClickedListener.onRecipeClicked(recipeList[position])
        }
    }

    fun setData(newRecipeList: MutableList<Recipe>) {
        recipeList.clear()
        recipeList.addAll(newRecipeList)
        notifyItemRangeInserted(0, recipeList.size)
    }

    interface RecipeItemClickListener {
        fun onRecipeClicked(recipe: Recipe)
    }
}