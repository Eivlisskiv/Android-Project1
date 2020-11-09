package caqc.cgodin.android_project1

import android.content.res.XmlResourceParser
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text
import java.lang.Exception

abstract class ActivityExtension : AppCompatActivity() {

    var languageDependantViews: Array<Int>? = null;

    /*
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }
    */

    fun setContentView(layoutResID:Int, views: Array<Int>?){
        super.setContentView(layoutResID)
        if(views != null) languageDependantViews = views;
        updateViewLanguage()
    }

    fun updateViewLanguage(){
        languageDependantViews?.forEach {
            val view = findViewById<TextView>(it)
            Log.i("Debug", "Get string value for $it")
            view.text = Utils.findViewString(this, it)
        }
    }
}