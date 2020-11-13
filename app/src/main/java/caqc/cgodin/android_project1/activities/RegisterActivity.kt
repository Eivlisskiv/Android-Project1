package caqc.cgodin.android_project1.activities

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import caqc.cgodin.android_project1.R
import caqc.cgodin.android_project1.Session
import caqc.cgodin.android_project1.Utils
import caqc.cgodin.android_project1.sqlite.DatabaseHandler
import caqc.cgodin.android_project1.sqlite.models.User
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlin.random.Random

class RegisterActivity : ActivityExtension() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            R.layout.activity_register, arrayOf(
                R.id.register_email_label, R.id.register_password_label,
                R.id.register_cancel_button, R.id.register_register_button
        ))
    }

    fun onClick_register(@Suppress("UNUSED_PARAMETER") v:View?){
        if(!verifyInputs(register_email_tb, register_password_tb) { tb, isEmpty ->
                var errorCode : String? = if(isEmpty) "empty" else null;
                when(tb.id){
                    R.id.register_email_tb -> {
                        if(errorCode == null && !Utils.stringMatch(tb.text.toString(), "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")) errorCode = "notEmail";
                    }
                }
                errorCode
            }) return;


        val email = register_email_tb.text;
        val pwd = register_password_tb.text;

        if(User.getUser(email.toString()) != null) {
            register_email_tb.error = Utils.getLangString(this, "tb_error_usedEmail")
            return
        }

        val user = User(email.toString(), pwd.toString(), "User${Random.nextInt(1000, 9999)}")
        DatabaseHandler.database.insert(user);
        Session.connect(user);
        switchActivity(ListActivity::class.java, null)
    }

    fun onClick_cancel(@Suppress("UNUSED_PARAMETER") v: View?){
        switchActivity(MainActivity::class.java, null)
    }

    fun test(){
        val users = DatabaseHandler.database.query(User::class,"select * from User", null)
        Log.i("App", users.joinToString(separator = "\n") { "${it.username} ${it.email} ${it.password}" } )
    }
}