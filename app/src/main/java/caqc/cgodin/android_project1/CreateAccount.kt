package caqc.cgodin.android_project1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import caqc.cgodin.android_project1.sqlite.DatabaseHandler
import caqc.cgodin.android_project1.sqlite.SqlEntity
import caqc.cgodin.android_project1.sqlite.models.User
import kotlinx.android.synthetic.main.create_account.*
import kotlin.random.Random

class CreateAccount : ActivityExtension() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_account, arrayOf(
            R.id.register_email_label, R.id.register_password_label,
            R.id.register_cancel_button, R.id.register_register_button
        ))
    }

    fun onClick_register(view:View?){
        val email = register_email_tb.text;
        val pwd = register_password_tb.text;

        if(email.isEmpty() || email.isNullOrBlank() || pwd.isEmpty() || pwd.isNullOrBlank()){
            return
        }

        val user = User(email.toString(), pwd.toString(), "User${Random.nextInt(1000, 9999)}")
        DatabaseHandler.database.insert(user);

        //TODO switch to profile or main map
        //startActivity(Intent(this@CreateAccount, MainActivity::class.java))
    }

    fun onClick_cancel(view: View?){
        test();
        //startActivity(Intent(this@CreateAccount, MainActivity::class.java))
    }

    fun test(){
        val users = DatabaseHandler.database.query( User::class,"select * from User")
        Log.i("App", users.joinToString(separator = "\n") { "${it.username} ${it.email} ${it.password}" } )
    }
}