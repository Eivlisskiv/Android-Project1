package caqc.cgodin.android_project1.sqlite.models

import android.database.Cursor
import caqc.cgodin.android_project1.AndroidProject1
import caqc.cgodin.android_project1.GooglePlaceQuery
import caqc.cgodin.android_project1.Session
import caqc.cgodin.android_project1.activities.ActivityExtension
import caqc.cgodin.android_project1.activities.RestoActivity
import caqc.cgodin.android_project1.sqlite.DatabaseHandler
import caqc.cgodin.android_project1.sqlite.SqlEntity
import com.google.android.libraries.places.api.model.Place
import org.json.JSONObject

class Restaurant() : SqlEntity(Restaurant::class) {

    companion object{

        fun queryMany(query:String): List<Restaurant>? {
            return DatabaseHandler.database.query(Restaurant::class,query, null)
        }

        fun query(query:String): Restaurant?{
            val restaurants = queryMany(query);
            return if(restaurants != null && restaurants.isNotEmpty()) restaurants.first() else null;
        }

        fun getRestaurant(id:String, email:String): Restaurant? = query("select * from restaurant where id = \"$id\" and email = \"$email\"")
        fun getRestaurant(id:String): Restaurant? = query("select * from restaurant where id = \"$id\"")
        fun getRestaurant(): List<Restaurant>? = queryMany("select * from Restaurant")

    }

    var email: String? = null
    var name: String? = null
    var id: String? = null
    var logoUrl: String? = null
    var address: String? = null;
    var website: String? = null;
    var phone: String? = null;

    var latitude: Double? = null
    var longitude: Double? = null

    constructor(email: String, name: String, id:String, logoUrl: String, latitude: Double, longitude: Double) : this(){
        this.email = email
        this.name = name
        this.id = id
        this.logoUrl = logoUrl
        this.latitude = latitude
        this.longitude = longitude
    }

    constructor(json: JSONObject) : this(){
        this.name = json.getString("name")
        this.id = json.getString("place_id")
        this.logoUrl = json.getString("icon")
        val location = json.getJSONObject("geometry").getJSONObject("location")
        this.latitude = location.getDouble("lat")
        this.longitude = location.getDouble("lng")

        //this.address = json.getString("adr_address")
        //this.website = json.getString("website")
        //this.phone = json.getString("formatted_phone_number")
    }

    fun isFav() : Boolean = email != null

    fun <T : ActivityExtension> inspect(activity: T){
        Session.current_session!!.inspectedRestoraunt = this;
        GooglePlaceQuery.getPlace(this.id!!, Place.Field.ADDRESS, Place.Field.WEBSITE_URI, Place.Field.PHONE_NUMBER){
            loadPlace(it)
            activity.switchActivity(RestoActivity::class)
        }
    }

    fun loadPlace(place : Place){
        this.address = place.address;
        this.website = place.websiteUri.toString();
        this.phone = place.phoneNumber;
    }
}