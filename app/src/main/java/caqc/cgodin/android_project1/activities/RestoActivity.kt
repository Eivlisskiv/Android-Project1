package caqc.cgodin.android_project1.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import caqc.cgodin.android_project1.R
import caqc.cgodin.android_project1.Session
import caqc.cgodin.android_project1.Utils
import caqc.cgodin.android_project1.sqlite.models.Restaurant
import caqc.cgodin.android_project1.ui.main.MapsFragment
import caqc.cgodin.android_project1.ui.main.MenuFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class RestoActivity : ActivityExtension(R.id.RestoToolbar) {

    lateinit var mapFrag: MapsFragment;
    lateinit var resto: Restaurant;
    lateinit var button: Button;
    lateinit var menuFrag: MenuFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resto)

        mapFrag = setFragment(MapsFragment::class, R.id.resto_map_frag)
        menuFrag = setFragment(MenuFragment::class, R.id.resto_menu_frag)
        initActivityData()
    }

    fun initActivityData(){
        val r = Session.current_session!!.inspectedRestoraunt;
        if(r == null) {
            switchActivity(ExploreActivity::class)
            return
        }
        resto = r;

        initTextViews()
        Utils.setGlideImage(this, R.id.resto_icon_image, resto.logoUrl ?: "")
        initButton()
    }

    fun initTextViews(){
        val lblNom = findViewById<TextView>(R.id.resto_nom_label)
        lblNom.text = resto.name;
        val lblAdresse = findViewById<TextView>(R.id.resto_adresse_label)
        lblAdresse.text = resto.id
    }

    fun initButton(){
        button = findViewById<Button>(R.id.resto_fav_btn)
        button.text = if(resto.isFav()) "Unfav" else "fav"
    }

    fun onClick(v: View?){
        Session.current_session?.favorite(resto);
        button.text = if(resto.isFav()) "Unfav" else "fav"
    }
}