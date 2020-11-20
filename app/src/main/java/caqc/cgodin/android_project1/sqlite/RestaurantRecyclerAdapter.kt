package caqc.cgodin.android_project1.sqlite

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import caqc.cgodin.android_project1.R
import caqc.cgodin.android_project1.Session
import caqc.cgodin.android_project1.activities.ActivityExtension
import caqc.cgodin.android_project1.activities.ExploreActivity
import caqc.cgodin.android_project1.activities.RestoActivity
import caqc.cgodin.android_project1.sqlite.models.Restaurant
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class RestaurantRecyclerAdapter(val activity: ActivityExtension)  : RecyclerView.Adapter<RestaurantRecyclerAdapter.RestaurantViewHolder>(){

    private var items: List<Restaurant> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        return RestaurantViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.restaurant_recycler_view, parent, false), activity)
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
        this.notifyDataSetChanged()
    }

    class RestaurantViewHolder(val itemView: View, val activity: ActivityExtension) : RecyclerView.ViewHolder(itemView){
        val restaurantLogo = itemView.findViewById<ImageView>(R.id.restaurant_logo)
        val restaurantName = itemView.findViewById<TextView>(R.id.restaurant_name)
        val restaurantAdress = itemView.findViewById<TextView>(R.id.restaurant_adress)
        val restaurantBtn = itemView.findViewById<Button>(R.id.Btn_info_resto)
        val restaurantCardview = itemView.findViewById<CardView>(R.id.Cardview_resto)

        fun bind(restaurant: Restaurant){
            restaurantName.text = restaurant.name
            restaurantAdress.text = restaurant.id

            restaurantBtn.setOnClickListener {
                Session.current_session?.inspectedRestoraunt = restaurant
                activity.switchActivity(RestoActivity::class)
            }

            restaurantCardview.setOnClickListener {
                when(activity){
                    is ExploreActivity -> activity.mapFrag.zoomTo(restaurant.latitude, restaurant.longitude)
                }
            }

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