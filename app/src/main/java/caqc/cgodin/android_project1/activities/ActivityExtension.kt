package caqc.cgodin.android_project1.activities

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import caqc.cgodin.android_project1.R
import caqc.cgodin.android_project1.Session
import caqc.cgodin.android_project1.Utils
import caqc.cgodin.android_project1.sqlite.RestaurantRecyclerAdapter
import caqc.cgodin.android_project1.sqlite.TopSpacingItemDecoration
import caqc.cgodin.android_project1.sqlite.models.Restaurant
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.fragment_restaurant_list.*
import org.w3c.dom.Text
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

abstract class ActivityExtension(var toolbarId: Int? = null) : AppCompatActivity() {

    var languageDependantViews: Array<Int>? = null
    val hasToolbar = toolbarId != null


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(hasToolbar){
            val inflater : MenuInflater = menuInflater
            inflater.inflate(R.menu.menu_toolbar, menu)
            return true
        }
        return false
    }

    fun setToolbar(){
        if (hasToolbar){
            val toolbar = findViewById<Toolbar>(toolbarId!!)
            setSupportActionBar(toolbar)
        }
    }

    fun setContentView(layoutResID:Int, views: Array<Int>?){
        super.setContentView(layoutResID)
        if(views != null) languageDependantViews = views
        updateViewLanguage()
        setToolbar()
    }

    fun updateViewLanguage(){
        languageDependantViews?.forEach {
            val view = findViewById<TextView>(it)
            Log.i("Debug", "Get string value for $it")
<<<<<<< HEAD
            if(view != null) view.text = Utils.findViewString(this, it)
=======
            if (view != null) view.text = Utils.findViewString(this, it)
>>>>>>> e9c10461f44d98020ce503a072dbd10d895f15b4
        }
    }

    fun verifyInputs(vararg inputs: EditText,
                     func: (tb: EditText, isEmpty: Boolean) -> String?): Boolean {
        //Initialize all inputs as valid
        var validInputs = true;
        //Loop to check all inputs
        for(input in inputs){
            //Get the error name is there is one
            val error = func(input,
                input.text.isNullOrEmpty() || input.text.isBlank())
            //If an error was returned
            if (error != null){
                //Apply error on input
                input.error = Utils.getLangString(this, "tbError_$error")
                validInputs = false
            }
        }
        return validInputs;
    }

    fun verifyInputs(vararg inputs: EditText): Boolean =
        verifyInputs(*inputs, func = { _, isEmpty ->  if (isEmpty) "empty" else null })

    fun <T : AppCompatActivity> switchActivity(clazz: KClass<T>, func: ((Intent) -> Intent)? = null): Intent{
        val intent = Intent(this, clazz.java)
        if(func != null) func(intent)
        startActivity(intent)
        return intent;
    }

    fun explore(item: MenuItem) =
        toolbar_btnClick(ExploreActivity::class)

    fun profile(item: MenuItem) =
        toolbar_btnClick(ProfileActivity::class)

    fun logout(item: MenuItem){
        Session.logout();
        toolbar_btnClick(MainActivity::class)
    }

    inline fun <reified T:ActivityExtension> toolbar_btnClick(clazz: KClass<T>){
        if(this is T) return
        switchActivity(clazz, null)
    }

    fun <T : Fragment> setFragment(clazz: KClass<T>, id: Int) : T {
        val instance = clazz.createInstance();
        supportFragmentManager.beginTransaction()
            .replace(id, instance).commit()
        return instance;
    }
}