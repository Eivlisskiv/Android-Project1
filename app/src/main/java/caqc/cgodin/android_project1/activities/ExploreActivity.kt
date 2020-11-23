package caqc.cgodin.android_project1.activities

import android.location.Location
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import caqc.cgodin.android_project1.R
import caqc.cgodin.android_project1.Session
import caqc.cgodin.android_project1.sqlite.RestaurantRecyclerAdapter
import caqc.cgodin.android_project1.sqlite.models.Restaurant
import caqc.cgodin.android_project1.ui.main.MapsFragment
import caqc.cgodin.android_project1.ui.main.RestaurantListFragment
import kotlinx.android.synthetic.main.activity_explore.*
import org.json.JSONObject
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

class ExploreActivity: ActivityExtension(R.id.explore_Toolbar) {

    lateinit var mapFrag: MapsFragment;
    lateinit var listFrag: RestaurantListFragment

    val distance: Int
        get() = explore_distance_bar?.progress ?: 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explore, arrayOf(R.id.explore_distance_label, R.id.explore_search_button))

        mapFrag = setFragment(MapsFragment::class, R.id.explore_map_frag)
        listFrag = setFragment(RestaurantListFragment::class, R.id.explore_list_frag)
        listFrag.activityParent = this
        listFrag.addData = Session.current_session?.searchResult
    }

    override fun onStart() {
        super.onStart()
        setSeekbar();
        setEditText();
    }

    fun setSeekbar(){
        explore_distance_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                explore_distance_tb.setText(progress.toString())
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
    }

    fun setEditText(){
        explore_distance_tb.setText(distance.toString());
        explore_distance_tb.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val i: Int = if(s.isNotEmpty()) s.toString().toInt() else 0;
                if(i < 0 || i > 20){
                    explore_distance_tb.error = "!"
                }
                else explore_distance_bar.progress = i;
            }

            override fun afterTextChanged(p0: Editable?) { }
        })
    }

    fun onClickSearch(v: View?){
        mapFrag.clear()
        mapFrag.googlePlacesQuery(listFrag,distance + 0.00) { Session.parseJsonResult(it); }
    }
}