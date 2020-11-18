package caqc.cgodin.android_project1.sqlite.models

import android.database.Cursor
import caqc.cgodin.android_project1.sqlite.DatabaseHandler
import caqc.cgodin.android_project1.sqlite.SqlEntity
import org.json.JSONObject

class Restaurant() : SqlEntity(Restaurant::class) {

    companion object{
        fun getRestaurant(id:String): Restaurant? {
            val restaurants =
                DatabaseHandler.database.query(Restaurant::class,"select * from restaurant where id = \"$id\""){
                    res, cursor ->
                    res.asignCursorData(cursor)
                }
            return if(restaurants.isNotEmpty()) restaurants.first() else null;
        }

        fun getRestaurant(): List<Restaurant>? {
            val restaurants =
                DatabaseHandler.database.query(Restaurant::class,"select * from Restaurant"){
                        res, cursor ->
                    res.asignCursorData(cursor)
                }
            return if(restaurants.isNotEmpty()) restaurants else null
        }
    }

    var email: String? = null
    var name: String? = null
    var id: String? = null
    var logoUrl: String? = null
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

    fun asignCursorData(cursor: Cursor){
        this.email = cursor.getString(0)
        this.name = cursor.getString(1)
        this.id = cursor.getString(2)
        this.logoUrl = cursor.getString(3)
        this.latitude = cursor.getDouble(4)
        this.longitude = cursor.getDouble(5)
    }

    constructor(json: JSONObject) : this(){
        this.name = json.getString("name")
        this.id = json.getString("place_id")
        this.logoUrl = json.getString("icon")
        val location = json.getJSONObject("geometry").getJSONObject("location")
        this.latitude = location.getDouble("lat")
        this.longitude = location.getDouble("lng")
    }

}