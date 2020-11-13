package caqc.cgodin.android_project1.sqlite.models

import caqc.cgodin.android_project1.sqlite.DatabaseHandler
import caqc.cgodin.android_project1.sqlite.SqlEntity

class Restaurant() : SqlEntity(Restaurant::class) {

    companion object{
        fun getRestaurant(id:String): Restaurant? {
            val restaurants =
                DatabaseHandler.database.query(Restaurant::class,"select * from restaurant where id = \"$id\""){
                    res, cursor ->
                    res.name = cursor.getString(0);
                    res.address = cursor.getString(1);
                    res.b64Logo = cursor.getString(2);
                };
            return if(restaurants.isNotEmpty()) restaurants.first() else null;
        }
    }

    var name: String? = null
    var address: String? = null
    var b64Logo: String? = null

    constructor(name: String, address:String, b64Logo: String) : this(){
        this.name = name;
        this.address = address;
        this.b64Logo = b64Logo;
    }
}