package caqc.cgodin.android_project1.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.Toolbar
import caqc.cgodin.android_project1.R
import caqc.cgodin.android_project1.Session
import caqc.cgodin.android_project1.Utils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity() : ActivityExtension(R.id.mainToolbar) {

    override fun onCreate(savedInstanceState: Bundle?) {

        if(Session.logged) switchActivity(ListActivity::class.java, null)
        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_main, arrayOf(
                R.id.login_email_label, R.id.login_password_label,
                R.id.login_login_button, R.id.login_register_button
        ))
    }

    fun login_button(@Suppress("UNUSED_PARAMETER") v: View?){

        if(!verifyInputs(login_email_tb, login_password_tb) { tb, isEmpty ->
                var errorCode : String? = if(isEmpty) "empty" else null;
                    when(tb.id){
                        R.id.login_email_tb -> {
                            if(errorCode == null && !Utils.stringMatch(tb.text.toString(), "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")) errorCode = "notEmail";
                        }
                    }
                errorCode
            }) return;

        if(Session.connect(
                login_email_tb.text.toString(),
                login_password_tb.text.toString())){
            switchActivity(ExploreActivity::class.java, null)
        }
        else{
            Log.i("App","Login failed")
            //Invalid log in info
        }
    }

    fun register(@Suppress("UNUSED_PARAMETER") v: View?){
        switchActivity(RegisterActivity::class.java, null)
    }
}