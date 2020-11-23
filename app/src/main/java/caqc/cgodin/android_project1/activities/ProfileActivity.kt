package caqc.cgodin.android_project1.activities

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import caqc.cgodin.android_project1.R
import caqc.cgodin.android_project1.Session
import caqc.cgodin.android_project1.sqlite.models.Restaurant
import caqc.cgodin.android_project1.ui.main.ProfileInfoFragment
import caqc.cgodin.android_project1.ui.main.RestaurantListFragment

class ProfileActivity : ActivityExtension(R.id.profileToolbar) {

    lateinit var listFrag: RestaurantListFragment
    lateinit var infoFrag: ProfileInfoFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        listFrag = setFragment(RestaurantListFragment::class, R.id.profile_list_frag)
        infoFrag = setFragment(ProfileInfoFragment::class, R.id.profile_info_frag)

        setContentView(R.layout.activity_profile, arrayOf(R.id.ShowFav))

        listFrag.activityParent = this
        infoFrag.activityParent = this

        listFrag.addData = Session.current_session?.getFavorited() as ArrayList<Restaurant>?
    }
}