package caqc.cgodin.android_project1.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import caqc.cgodin.android_project1.R
import caqc.cgodin.android_project1.Session
import caqc.cgodin.android_project1.activities.ActivityExtension
import caqc.cgodin.android_project1.activities.ExploreActivity
import caqc.cgodin.android_project1.sqlite.TopSpacingItemDecoration
import caqc.cgodin.android_project1.sqlite.models.Restaurant
import caqc.cgodin.android_project1.sqlite.recyclerview.RecyclerViewHandler
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class RestaurantListFragment : Fragment() {

    var addData: ArrayList<Restaurant>? = null
    var mapFrag: MapsFragment? = null;
    lateinit var restaurantAdapter: RecyclerViewHandler<Restaurant> //RestaurantRecyclerAdapter
    lateinit var activityParent: ActivityExtension

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val inflater = inflater.inflate(R.layout.fragment_restaurant_list, container, false)
        var recyclerview = inflater.findViewById<RecyclerView>(R.id.restaurant_recyclerview)
        initRecyclerView(recyclerview)
        addDataSet()
        return inflater
    }

    fun addDataSet(){
        if (addData != null) {
            restaurantAdapter.submitList(addData!!)
        }
    }

    fun initRecyclerView(recyclerview: RecyclerView) {
        recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            val topSpacingDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingDecoration)

            //RestaurantRecyclerAdapter(activityParent)
            restaurantAdapter = RecyclerViewHandler(activityParent, R.layout.restaurant_recycler_view){
                itemView, restaurant ->
                bind(itemView, restaurant);
            }
            adapter = restaurantAdapter
        }
    }

    fun bind(itemView: View, restaurant: Restaurant){
        val restaurantLogo = itemView.findViewById<ImageView>(R.id.restaurant_logo)
        val restaurantName = itemView.findViewById<TextView>(R.id.restaurant_name)
        val restaurantAdress = itemView.findViewById<TextView>(R.id.restaurant_adress)
        val restaurantBtn = itemView.findViewById<Button>(R.id.Btn_info_resto)
        val restaurantCardview = itemView.findViewById<CardView>(R.id.Cardview_resto)
        val restaurantIsFav = itemView.findViewById<ImageView>(R.id.fav_star)

        restaurantName.text = restaurant.name
        restaurantAdress.text = restaurant.address

        Session.current_session?.verifyFav(restaurant)
        restaurantIsFav.imageAlpha = if(restaurant.isFav()) 255 else 0

        restaurantBtn.setOnClickListener {
            restaurant.inspect(activity as ActivityExtension)
        }

        restaurantCardview.setOnClickListener {
            when(activity){
                is ExploreActivity -> mapFrag?.zoomTo(restaurant.latitude, restaurant.longitude)
            }
        }

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)

        Glide.with(itemView.context)
            .applyDefaultRequestOptions(requestOptions)
            .load(restaurant.imageUrl)
            .into(restaurantLogo)
    }

    fun submitList(items: ArrayList<Restaurant>) = restaurantAdapter.submitList(items)
}