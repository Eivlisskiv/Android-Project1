package caqc.cgodin.android_project1.sqlite

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import caqc.cgodin.android_project1.R
import caqc.cgodin.android_project1.sqlite.models.Restaurant
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class RestaurantRecyclerAdapter  : RecyclerView.Adapter<RestaurantRecyclerAdapter.RestaurantViewHolder>(){

    private var items: List<Restaurant> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        return RestaurantViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.restaurant_recycler_view, parent, false))
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(restaurantList: List<Restaurant>){
        Log.i("SubmitList", "DANS SUBMITLIST ***********************************************")
        for(resto in restaurantList){
            Log.i("Resto", "${resto.name}: ${resto.latitude ?: 0.0}, ${resto.longitude ?: 0.0}")
        }
        items = restaurantList
    }

    class RestaurantViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView){


        fun bind(restaurant: Restaurant){
            val restaurantLogo = itemView.findViewById<ImageView>(R.id.restaurant_logo)
            val restaurantName = itemView.findViewById<TextView>(R.id.restaurant_name)
            val restaurantAdress = itemView.findViewById<TextView>(R.id.restaurant_adress)

            restaurantName.text = restaurant.name
            restaurantAdress.text = restaurant.id

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