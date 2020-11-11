package caqc.cgodin.android_project1.sqlite.models

import caqc.cgodin.android_project1.sqlite.DatabaseHandler
import caqc.cgodin.android_project1.sqlite.SqlEntity

class User() : SqlEntity(User::class){

    companion object{
        fun getUser(email:String): User? {
            val users =
                DatabaseHandler.database.query(User::class,"select * from user where email = \"$email\"");
            return if(users.isNotEmpty()) users.first() else null;
        }
    }

    var email: String? = null
    var password: String? = null
    var username: String? = null

    constructor(email: String, password:String, username: String) : this(){
        this.email = email
        this.password = password;
        this.username = username;
    }
}