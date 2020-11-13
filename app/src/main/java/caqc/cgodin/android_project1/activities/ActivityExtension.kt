package caqc.cgodin.android_project1.activities

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import caqc.cgodin.android_project1.Utils
import org.w3c.dom.Text

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

    fun <T : AppCompatActivity> switchActivity(clazz: Class<T>,
                                               func: ((Intent) -> Intent)?): Intent{
        val intent = Intent(this, clazz)
        if(func != null) func(intent);
        startActivity(intent)
        return intent;
    }

}