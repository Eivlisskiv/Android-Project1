package caqc.cgodin.android_project1

import android.location.Location
import android.util.Log
import caqc.cgodin.android_project1.sqlite.DatabaseHandler
import caqc.cgodin.android_project1.sqlite.models.Restaurant
import caqc.cgodin.android_project1.sqlite.models.User
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONObject
import kotlin.random.Random

//A logged session data
class Session {

    companion object{

        var current_session: Session? = Session()
            private set

        val logged: Boolean
            get() = current_session?.email != null

        fun connect(email:String, pwd: String): Boolean{
            val user = User.getUser(email);
            if(user == null || user.password != pwd) return false;
            current_session = Session(user)
            return true
        }

        fun connect(user:User) {
            current_session = Session(user)
        }

        fun connect(account: GoogleSignInAccount) {
            current_session = Session(account)
        }

        fun connect(result: LoginResult){
            current_session = Session(result)
        }

        fun logout() {
            current_session = null;
        }

        fun parseJsonResult(json: JSONObject) = current_session?.parseJsonResult(json);
    }

    var inspectedRestoraunt: Restaurant? = null
        get() = field
        set(value) {
            field = value;
            if(field != null && logged){
                val r= Restaurant.getRestaurant(email!!, field!!.id!!)
                if(r != null) field!!.email = email;
            }
        }
    var location : Location = Location("");

    var searchResult : ArrayList<Restaurant> = arrayListOf()
        private set;

    var email:String? = null;

    constructor(user: User){
        email = user.email;
    }

    constructor(account: GoogleSignInAccount){
        email = account.email;
    }

    constructor(result: LoginResult){
        email = result.accessToken.userId;
    }

    constructor(){

    }

    fun favorite(resto: Restaurant){
        //Not logged, quit
        if(email == null) return;

        //Already fav, remove from db
        if(resto.isFav()){
            DatabaseHandler.database.remove("restaurant", "email = \"$email\" and id = \"${resto.id}\"")
        }else{//insert to db
            resto.email = email;
            DatabaseHandler.database.insert(resto)
        }
    }

    fun parseJsonResult(json: JSONObject) {

        val results = json.getJSONArray("results")
        if(results.length() < 1) {
            searchResult = arrayListOf()
            return;
        }

        val list : ArrayList<Restaurant> = arrayListOf()
        val l = results.length() - 1
        for(i in 0..l){
            val resto = Restaurant(results.getJSONObject(i))
            list.add(resto);
        }

        searchResult = list;
    }

    fun getSessionLocation() : Location{
        when(email){
            "debug@m.ca" -> {
                location.latitude = 45.8
                location.longitude = -73.8
            }
            "phile@wtv.com" -> {
                location.latitude = 45.8
                location.longitude = -73.8
            }
        }

        return location
    }

    fun getFavorited(): List<Restaurant>? = if (email != null) Restaurant.queryMany("select * from restaurant where email = \"$email\"") else null;
}