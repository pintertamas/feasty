package hu.bme.aut.feasty.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.feasty.databinding.IngredientItemBinding
import hu.bme.aut.feasty.model.Ingredient

class IngredientListAdapter :
    RecyclerView.Adapter<IngredientListAdapter.IngredientListViewHolder>() {
    private var ingredientList = mutableListOf<Ingredient>()

    inner class IngredientListViewHolder(val binding: IngredientItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = IngredientListViewHolder(
        IngredientItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int {
        return ingredientList.size
    }

    override fun onBindViewHolder(holder: IngredientListViewHolder, position: Int) {
        holder.binding.ingredientName.text = ingredientList[holder.adapterPosition].name
        (ingredientList[holder.adapterPosition].measures.metric.amount.toString() + " " +
                ingredientList[holder.adapterPosition].measures.metric.unitShort).also {
            holder.binding.ingredientAmount.text = it
        }
    }

    fun setData(newIngredientList: MutableList<Ingredient>) {
        ingredientList = newIngredientList
        notifyItemRangeInserted(0, ingredientList.size)
    }
}