package hu.bme.aut.feasty.adapter

import android.graphics.BitmapFactory
import android.os.Looper
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.feasty.R
import hu.bme.aut.feasty.model.Recipe
import android.graphics.Bitmap
import com.squareup.picasso.Picasso
import hu.bme.aut.feasty.databinding.RecyclerViewItemBinding


class RecipeListAdapter : RecyclerView.Adapter<RecipeListAdapter.RecipeListViewHolder>() {

    private var recipeList = mutableListOf<Recipe>()

    inner class RecipeListViewHolder(val binding: RecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RecipeListViewHolder(
        RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int {
        System.out.println("size when getItemCount() was called: " + recipeList.size)
        return this.recipeList.size
    }

    override fun onBindViewHolder(holder: RecipeListViewHolder, position: Int) {
        System.out.println("onBindViewHolder recipe count: " + recipeList.size)
        holder.binding.title.text = recipeList[position].title
        ("ready in " + recipeList[position].readyInMinutes.toString() + " minutes").also { holder.binding.readyInMinutes.text = it }
        val imageURL = "https://spoonacular.com/recipeImages/" + recipeList[position].imageUri
        Picasso.get().load(imageURL).into(holder.binding.recipeImageCard)



        //val bmp = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream())
        //holder.binding.imageCard.background =


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

    fun setData(newRecipeList: MutableList<Recipe>) {
        recipeList.clear()
        recipeList.addAll(newRecipeList)

        System.out.println("recipe count after setData() $itemCount")
        notifyDataSetChanged()
    }
}