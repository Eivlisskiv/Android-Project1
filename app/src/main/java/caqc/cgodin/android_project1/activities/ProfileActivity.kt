package caqc.cgodin.android_project1.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import caqc.cgodin.android_project1.R
import caqc.cgodin.android_project1.sqlite.DatabaseHandler
import caqc.cgodin.android_project1.sqlite.models.Restaurant
import caqc.cgodin.android_project1.ui.main.RestaurantListFragment
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

class ProfileActivity : ActivityExtension(R.id.profileToolbar) {

    lateinit var listFrag: RestaurantListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        listFrag = setFragment(RestaurantListFragment::class, R.id.profile_list_frag)
    }

    fun <T : Fragment> setFragment(clazz: KClass<T>, id: Int) : T {
        val instance = clazz.createInstance();
        supportFragmentManager.beginTransaction()
            .replace(id, instance).commit()
        return instance;
    }
}