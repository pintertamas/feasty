package hu.bme.aut.feasty.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.feasty.model.Recipe
import com.squareup.picasso.Picasso
import hu.bme.aut.feasty.databinding.RecyclerViewItemBinding
import hu.bme.aut.feasty.model.RecipeRecord

class RecipeListAdapter(
    private val recipeItemClickedListener: RecipeItemClickListener,
    private val recyclerViewUpdatesListener: RecyclerViewUpdatesListener
) : RecyclerView.Adapter<RecipeListAdapter.RecipeListViewHolder>() {

    private var recipeList = mutableListOf<RecipeRecord>()


    class RecipeListViewHolder(val binding: RecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RecipeListViewHolder(
        RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int {
        return this.recipeList.size
    }

    override fun onBindViewHolder(holder: RecipeListViewHolder, position: Int) {
        holder.binding.title.text = recipeList[holder.adapterPosition].recipe.title
        ("ready in " + recipeList[holder.adapterPosition].recipe.readyInMinutes.toString() + " minutes").also {
            holder.binding.readyInMinutes.text = it
        }
        val imageURL =
            recipeList[holder.adapterPosition].recipe.imageUri
        Picasso.get().load(imageURL).into(holder.binding.recipeImageCard)

        holder.itemView.setOnClickListener {
            recipeItemClickedListener.onRecipeClicked(recipeList[holder.adapterPosition].recipe)
        }
    }

    private fun cleanList() {
        val oldSize = recipeList.size
        recipeList = mutableListOf()
        notifyItemRangeRemoved(0, oldSize)
    }

    fun setData(newRecipeList: MutableList<RecipeRecord>) {
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