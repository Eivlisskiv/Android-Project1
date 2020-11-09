package caqc.cgodin.android_project1

import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity() : ActivityExtension() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main, arrayOf(
            R.id.login_email_label, R.id.login_password_label,
            R.id.login_login_button
        ))
    }

    fun login_button(view: View?){
        this.updateViewLanguage()
    }
}