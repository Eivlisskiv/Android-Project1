package caqc.cgodin.android_project1.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import caqc.cgodin.android_project1.R
import caqc.cgodin.android_project1.Session
import caqc.cgodin.android_project1.Utils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*

class MainActivity() : ActivityExtension() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            R.layout.activity_main, arrayOf(
                R.id.login_email_label, R.id.login_password_label,
                R.id.login_login_button, R.id.login_register_button
        ))
    }

    fun login_button(view: View?){

        if(verifyInputs(login_email_tb, login_password_tb) { tb, isEmpty ->
                var errorCode : String? = null;
                    when(tb.id){
                        R.id.login_email_label -> {
                            //if(Regex tb.Text is not email) errorCode = "notEmail";
                        }
                    }
                if(isEmpty) "empty" else errorCode
            }) return;

        if(Session.connect(
                login_email_tb.text.toString(),
                login_password_tb.text.toString())){
            switchActivity(this@MainActivity, RegisterActivity::class.java, null)
        }
        else{
            Log.i("App","Login failed")
            //Invalid log in info
        }
    }

    fun register(view: View?){
        switchActivity(this@MainActivity, RegisterActivity::class.java, null)
    }
}