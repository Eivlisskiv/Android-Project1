package caqc.cgodin.android_project1.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import caqc.cgodin.android_project1.R
import caqc.cgodin.android_project1.Session
import caqc.cgodin.android_project1.sqlite.RestaurantRecyclerAdapter
import caqc.cgodin.android_project1.sqlite.TopSpacingItemDecoration
import caqc.cgodin.android_project1.sqlite.models.Restaurant
import kotlinx.android.synthetic.main.fragment_restaurant_list.*

class RestaurantListFragment : Fragment() {
    lateinit var restaurantAdapter: RestaurantRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
        val data = Session.current_session?.searchResult
        if (data != null) {
            restaurantAdapter.submitList(data)
        }
    }

    fun initRecyclerView(recyclerview: RecyclerView) {
        recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            val topSpacingDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingDecoration)
            restaurantAdapter = RestaurantRecyclerAdapter()
            adapter = restaurantAdapter
        }
    }
}