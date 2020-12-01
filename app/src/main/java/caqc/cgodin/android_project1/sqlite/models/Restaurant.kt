package caqc.cgodin.android_project1.sqlite.models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import caqc.cgodin.android_project1.Config
import caqc.cgodin.android_project1.GooglePlaceQuery
import caqc.cgodin.android_project1.Session
import caqc.cgodin.android_project1.activities.ActivityExtension
import caqc.cgodin.android_project1.activities.RestoActivity
import caqc.cgodin.android_project1.sqlite.DatabaseHandler
import caqc.cgodin.android_project1.sqlite.SqlEntity
import com.google.android.libraries.places.api.model.Place
import org.json.JSONObject
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

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
    var image: String? = null;
    var address: String? = null;
    var website: String? = null;
    var phone: String? = null;

    var latitude: Double? = null
    var longitude: Double? = null

    val imageUrl: String
        get() =
            if(this.image != null)
                "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=${this.image}&key=${Config.googletoken}"
            else this.logoUrl ?: "";


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
        this.image = loadImage(json);
        val location = json.getJSONObject("geometry").getJSONObject("location")
        this.latitude = location.getDouble("lat")
        this.longitude = location.getDouble("lng")

        this.address = json.getString("vicinity")
    }

    private fun loadImage(json:JSONObject): String? {
        if(!json.has("photos")) return null;

        val photosArray = json.getJSONArray("photos")
        val object1 = photosArray.getJSONObject(0)
        return object1.getString("photo_reference");
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

    fun getImage(){
        val url = URL("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=${this.image}&key=AIzaSyDc9eKG7hymCORhdJzW6DMqR0y0lC_yIdA%22")
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()
        val input: InputStream = connection.inputStream
        val temp: Bitmap = BitmapFactory.decodeStream(input)
    }
}