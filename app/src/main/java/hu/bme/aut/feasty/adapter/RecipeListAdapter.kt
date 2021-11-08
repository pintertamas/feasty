package hu.bme.aut.feasty.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.feasty.model.Recipe
import com.squareup.picasso.Picasso
import hu.bme.aut.feasty.databinding.RecyclerViewItemBinding

class RecipeListAdapter(
    private val recipeItemClickedListener: RecipeItemClickListener,
    private val recyclerViewUpdatesListener: RecyclerViewUpdatesListener
) : RecyclerView.Adapter<RecipeListAdapter.RecipeListViewHolder>() {

    private var recipeList = mutableListOf<Recipe>()


    class RecipeListViewHolder(val binding: RecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RecipeListViewHolder(
        RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int {
        return this.recipeList.size
    }

    override fun onBindViewHolder(holder: RecipeListViewHolder, position: Int) {
        holder.binding.title.text = recipeList[holder.adapterPosition].title
        ("ready in " + recipeList[holder.adapterPosition].readyInMinutes.toString() + " minutes").also {
            holder.binding.readyInMinutes.text = it
        }
        val imageURL =
            "https://spoonacular.com/recipeImages/" + recipeList[holder.adapterPosition].imageUri
        Picasso.get().load(imageURL).into(holder.binding.recipeImageCard)

        holder.itemView.setOnClickListener {
            recipeItemClickedListener.onRecipeClicked(recipeList[holder.adapterPosition])
        }
    }

    private fun cleanList() {
        val oldSize = recipeList.size
        recipeList = mutableListOf()
        notifyItemRangeRemoved(0, oldSize)
    }

    fun setData(newRecipeList: MutableList<Recipe>) {
        cleanList()
        recipeList = newRecipeList
        recyclerViewUpdatesListener.onRecyclerViewChanged(this.recipeList.size)
        notifyItemRangeInserted(0, recipeList.size)
    }

    interface RecipeItemClickListener {
        fun onRecipeClicked(recipe: Recipe)
    }

    interface RecyclerViewUpdatesListener {
        fun onRecyclerViewChanged(itemCount: Int)
    }
}