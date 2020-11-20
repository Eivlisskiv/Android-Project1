package caqc.cgodin.android_project1.activities

import android.R.attr
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import caqc.cgodin.android_project1.R
import caqc.cgodin.android_project1.Session
import caqc.cgodin.android_project1.Utils
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_main.*
import javax.security.auth.callback.Callback


class MainActivity() : ActivityExtension(R.id.mainToolbar) {

    val GOOGLE_SIGN_IN = 101;
    val FACEBOOK_SIGN_IN = 202;

    override fun onCreate(savedInstanceState: Bundle?) {

        if(Session.logged) switchActivity(ExploreActivity::class)
        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_main, arrayOf(
                R.id.login_email_label, R.id.login_password_label,
                R.id.login_login_button, R.id.login_register_button
            )
        )

        Utils.setGlideImage(this, R.id.login_facebook_btn, "https://insidesmallbusiness.com.au/wp-content/uploads/2014/07/facebook-logo.jpg")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode === GOOGLE_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }

    }

    fun login_button(@Suppress("UNUSED_PARAMETER") v: View?){

        if(!verifyInputs(login_email_tb, login_password_tb) { tb, isEmpty ->
                var errorCode : String? = if(isEmpty) "empty" else null;
                    when(tb.id){
                        R.id.login_email_tb -> {
                            if (errorCode == null && !Utils.stringMatch(
                                    tb.text.toString(),
                                    "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"
                                )
                            ) errorCode = "notEmail";
                        }
                    }
                errorCode
            }) return;

        if(Session.connect(
                login_email_tb.text.toString(),
                login_password_tb.text.toString()
            )){
            switchActivity(ExploreActivity::class)
        }
        else{
            Log.i("App", "Login failed")
            //Invalid log in info
        }
    }

    fun register(@Suppress("UNUSED_PARAMETER") v: View?) = switchActivity(RegisterActivity::class)

    fun login_google(view: View?){

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail() .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN)

    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            Session.connect(account);
            switchActivity(ExploreActivity::class)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Google Login", "signInResult:failed code=" + e.statusCode);
        }
    }

    fun loginFacebook(v:View?){
        //LoginManager.getInstance()
    }
}