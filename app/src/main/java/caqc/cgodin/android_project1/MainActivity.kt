package caqc.cgodin.android_project1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity() : ActivityExtension() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main, arrayOf(
            R.id.login_email_label, R.id.login_password_label,
            R.id.login_login_button, R.id.login_register_button
        ))
    }

    fun login_button(view: View?){
        if(Session.connect(login_email_tb.text.toString(),
                login_password_tb.text.toString())){
            Log.i("App", "Now logged as ${Session.current_session?.username}")
            startActivity(Intent(this@MainActivity, CreateAccount::class.java))
        }
        else{
            Log.i("App","Login failed")
            //Invalid log in info
        }
    }

    fun register(view: View?){
        val intent = Intent(this@MainActivity, CreateAccount::class.java)
        startActivity(intent)
    }

    fun logginSuccess(){

    }
}