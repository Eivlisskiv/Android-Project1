package caqc.cgodin.android_project1

import android.util.Log
import caqc.cgodin.android_project1.sqlite.models.User
import kotlin.random.Random

//A logged session data
class Session {
    companion object{
        var current_session: Session? = null
            private set

        fun connect(email:String, pwd: String): Boolean{
            val user = User.getUser(email);
            if(user == null || user.password != pwd) return false;
            current_session = Session(user)
            return true
        }

        fun anonymous(){
            current_session = Session()
        }
    }

    var username:String = "";

    constructor(user: User){
        username = user.username ?: "";
    }

    constructor(){
        username = "Anon" + Random.nextInt(1000, 9999);
    }
}