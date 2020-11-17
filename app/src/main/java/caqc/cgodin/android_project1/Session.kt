package caqc.cgodin.android_project1

import android.location.Location
import caqc.cgodin.android_project1.sqlite.models.User
import kotlin.random.Random

//A logged session data
class Session {
    companion object{
        var location : Location? = null;

        var current_session: Session? = null
            private set
        val logged: Boolean
            get() = current_session != null

        fun connect(email:String, pwd: String): Boolean{
            val user = User.getUser(email);
            if(user == null || user.password != pwd) return false;
            current_session = Session(user)
            return true
        }

        fun connect(user:User){
            current_session = Session(user);
        }
    }

    var email:String = "";

    constructor(user: User){
        email = user.email ?: "";
    }
}