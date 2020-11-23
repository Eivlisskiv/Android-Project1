package caqc.cgodin.android_project1.activities

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import caqc.cgodin.android_project1.R
import caqc.cgodin.android_project1.Session
import caqc.cgodin.android_project1.Utils
import caqc.cgodin.android_project1.sqlite.models.Restaurant
import caqc.cgodin.android_project1.ui.main.ProfileInfoFragment
import caqc.cgodin.android_project1.ui.main.RestaurantListFragment
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : ActivityExtension(R.id.profileToolbar) {

    lateinit var listFrag: RestaurantListFragment
    lateinit var infoFrag: ProfileInfoFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_profile)

        listFrag = setFragment(RestaurantListFragment::class, R.id.profile_list_frag)
        infoFrag = setFragment(ProfileInfoFragment::class, R.id.profile_info_frag)
        
        listFrag.activityParent = this
        infoFrag.activityParent = this

        listFrag.addData = Session.current_session?.getFavorited() as ArrayList<Restaurant>?
        if(listFrag.addData == null || listFrag.addData!!.isEmpty()) findViewById<TextView>(R.id.tv_add_fav).text = Utils.getLangString(this, "tv_add_fav")
        setToolbar()
    }
}