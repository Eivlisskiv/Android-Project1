package caqc.cgodin.android_project1.sqlite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import caqc.cgodin.android_project1.R
import caqc.cgodin.android_project1.sqlite.models.Restaurant
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.restaurant_recycler_view.view.*
import kotlin.collections.ArrayList

class RestaurantRecyclerAdapter  : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var items: List<Restaurant> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RestaurantViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.activity_profile, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is RestaurantViewHolder -> holder.bind(items.get(position))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(restaurantList: List<Restaurant>){
        items = restaurantList
    }

    class RestaurantViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        val restaurantLogo = itemView.restaurant_logo
        val restaurantName = itemView.restaurant_name
        val restaurantAdress = itemView.restaurant_adress

        fun bind(restaurant: Restaurant){
            restaurantName.setText(restaurant.name)
            restaurantAdress.setText(restaurant.id)

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(restaurant.logoUrl)
                .into(restaurantLogo)
        }
    }

}