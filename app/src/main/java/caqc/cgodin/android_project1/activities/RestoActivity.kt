package caqc.cgodin.android_project1.activities

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.textservice.SpellCheckerService
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import caqc.cgodin.android_project1.R
import caqc.cgodin.android_project1.Session
import caqc.cgodin.android_project1.Utils
import caqc.cgodin.android_project1.sqlite.DatabaseHandler
import caqc.cgodin.android_project1.sqlite.models.Commande
import caqc.cgodin.android_project1.sqlite.models.Restaurant
import caqc.cgodin.android_project1.ui.main.MapsFragment
import caqc.cgodin.android_project1.ui.main.MenuFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_resto.*

class RestoActivity : ActivityExtension(R.id.RestoToolbar) {

    lateinit var mapFrag: MapsFragment;
    lateinit var resto: Restaurant;
    lateinit var button: Button;
    lateinit var menuFrag: MenuFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resto, arrayOf(R.id.commander))

        mapFrag = setFragment(MapsFragment::class, R.id.resto_map_frag)
        menuFrag = setFragment(MenuFragment::class, R.id.resto_menu_frag)
        initActivityData()
        mapFrag.zoomTo(resto.latitude, resto.longitude)
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
        findViewById<TextView>(R.id.resto_nom_label).text = resto.name;
        findViewById<TextView>(R.id.resto_adresse_label).text = resto.address
        initWebsite()
        findViewById<TextView>(R.id.resto_phone_label).text = resto.phone
    }

    fun initWebsite(){
        val web = findViewById<TextView>(R.id.resto_website_label)
        if(resto.website == null || resto.website!!.isEmpty()){
            web.text = "";
            return;
        }
        web.text = resto.website;
        web.movementMethod = LinkMovementMethod.getInstance()
        web.setLinkTextColor(Color.BLUE);
        web.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(resto.website))
            startActivity(browserIntent)
        }
    }

    fun initButton(){
        button = findViewById<Button>(R.id.resto_fav_btn)
        button.text = if(resto.isFav()) "Unfav" else "fav"
    }

    fun onClick(v: View?){
        Session.current_session?.favorite(resto);
        button.text = if(resto.isFav()) "Unfav" else "fav"
    }

    fun commander(v: View?){
        val commandContent = tb_commande.text.toString().trim();
        val commande = Utils.getLangString(this, "commande").format(commandContent)
        Toast.makeText(applicationContext,commande,Toast.LENGTH_SHORT).show()
        if(Session.logged) {
            val cmd = Commande(resto.id!!, Session.current_session?.email!!, commandContent)
            DatabaseHandler.database.insert(cmd);
        }
        tb_commande.text = null
    }
}