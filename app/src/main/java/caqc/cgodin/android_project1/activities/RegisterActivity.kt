package caqc.cgodin.android_project1.activities

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import caqc.cgodin.android_project1.R
import caqc.cgodin.android_project1.Session
import caqc.cgodin.android_project1.sqlite.DatabaseHandler
import caqc.cgodin.android_project1.sqlite.models.User
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

    fun onClick_register(v:View?){
        if(!verifyInputs(register_email_tb, register_password_tb)) return;

        val email = register_email_tb.text;
        val pwd = register_password_tb.text;

        val user = User(email.toString(), pwd.toString(), "User${Random.nextInt(1000, 9999)}")
        DatabaseHandler.database.insert(user);
        Session.connect(user);
        switchActivity(ListActivity::class.java, null)
    }

    fun onClick_cancel(v: View?){
        switchActivity(MainActivity::class.java, null)
    }

    fun test(){
        val users = DatabaseHandler.database.query(User::class,"select * from User", null)
        Log.i("App", users.joinToString(separator = "\n") { "${it.username} ${it.email} ${it.password}" } )
    }
}