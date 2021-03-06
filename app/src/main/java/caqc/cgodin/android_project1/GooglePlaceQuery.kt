package caqc.cgodin.android_project1

import android.location.Location
import android.util.Log
import caqc.cgodin.android_project1.activities.MainActivity
import caqc.cgodin.android_project1.sqlite.models.Restaurant
import com.google.android.gms.tasks.Task
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FetchPlaceResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.*
import okhttp3.internal.wait
import org.json.JSONObject
import java.io.IOException
import java.net.URL

const val prefix = "https://maps.googleapis.com/maps/api/place/"
enum class SearchType(val value:String){
    Nearby("nearbysearch"),
}

//                     //&type=restaurants
class GooglePlaceQuery(var inputParam:String, var location: Location, var distance: Double, val type: SearchType = SearchType.Nearby, vararg val fields: String) {

    companion object{
        fun getPlace(id:String, vararg fields: Place.Field, func: (Place) -> Unit) {
            val request = FetchPlaceRequest.builder(id, fields.asList()).build()
            val task = AndroidProject1.placesClient.fetchPlace(request)
            task.addOnSuccessListener { func(it.place) }
        }

    }

    val client: OkHttpClient = OkHttpClient()

    val keyParam = "key=${Config.googletoken}"

    val locationParam: String
        get() = "location=${location.latitude},${location.longitude}"
    val radiusParam: String
        get() = "radius=$distance"

    val url: String
        get() = "$prefix${type.value}/json?${parseParams()}${parseFields()}&$keyParam"

    fun parseParams(): String {
        return arrayOf(inputParam, locationParam, radiusParam).joinToString(separator = "&")
    }

    fun parseFields() : String{
        if(fields.isEmpty()) return "";

        return "&fields=${fields.joinToString(separator = ",")}"
    }

    fun request(callback: (JSONObject) -> Unit){
        val url = this.url;
        Log.i("Request", "URL: $url")
        val request = Request.Builder().url(url).build();
        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if(response.isSuccessful && response.body != null){
                    val json = JSONObject(response.body!!.string())
                    callback(json);
                }else Log.i("Request", "Response was not successful " + response.code)
            }
        })
    }
}