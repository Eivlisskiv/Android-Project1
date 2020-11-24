package caqc.cgodin.android_project1

import android.app.Application
import android.content.Context
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient

class AndroidProject1 : Application() {

    companion object {
        var context: Context? = null
            private set

        lateinit var placesClient : PlacesClient;
    }

    override fun onCreate() {
        super.onCreate()
        Places.initialize(applicationContext, Config.googletoken)
        context = applicationContext
        placesClient = Places.createClient(applicationContext)
    }
}