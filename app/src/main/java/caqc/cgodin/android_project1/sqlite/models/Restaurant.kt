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
    var address: String? = null
    var b64Logo: String? = null
    var latitude: String? = null
    var longitude: String? = null

    constructor(email: String, name: String, address:String, b64Logo: String, latitude: String, longitude: String) : this(){
        this.email = email
        this.name = name
        this.address = address
        this.b64Logo = b64Logo
        this.latitude = latitude
        this.longitude = longitude
    }

    fun asignCursorData(cursor: Cursor){
        this.email = cursor.getString(0)
        this.name = cursor.getString(1)
        this.address = cursor.getString(2)
        this.b64Logo = cursor.getString(3)
        this.latitude = cursor.getString(4)//.toDouble()
        this.longitude = cursor.getString(5)//.toDouble()
    }

    constructor(json: JSONObject) : this(){

    }

}