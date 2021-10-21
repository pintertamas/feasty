package hu.bme.aut.feasty.adaptor

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.feasty.databinding.RecyclerViewItemBinding
import hu.bme.aut.feasty.model.RecipeList

class RecipeListAdapter : RecyclerView.Adapter<RecipeListAdapter.RecipeListViewHolder>() {

    private var recipeList: RecipeList = RecipeList(emptyList(), "",0)

    inner class RecipeListViewHolder(val binding: RecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RecipeListViewHolder(
        RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int {
        System.out.println("size when getItemCount() was called: " + recipeList.recipes.size) // TODO: ez itt 0 valamiért
        return recipeList.recipes.size
    }

    override fun onBindViewHolder(holder: RecipeListViewHolder, position: Int) {
        System.out.println("onBindViewHolder recipe count: " + recipeList.recipes.size)
        holder.binding.title.text = recipeList.recipes[position].title
        holder.binding.readyInMinutes.text = recipeList.recipes[position].title
        //val imageURL = recipeList.recipes[position].imageUri

        /*try {
            val handler = Handler(Looper.getMainLooper())
            val imageView = holder.binding.imageCard.findViewById<ImageView>(R.id.recipe_image_card)
            val `in` = java.net.URL(imageURL).openStream()
            val image = BitmapFactory.decodeStream(`in`)

            // Only for making changes in UI
            handler.post {
                imageView.setImageBitmap(image)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }*/
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newRecipeList: RecipeList) {
        recipeList = newRecipeList
        System.out.println("recipe count after setData() " + recipeList.recipes.size)
        notifyItemRangeInserted(0, recipeList.recipes.size)
    }
}