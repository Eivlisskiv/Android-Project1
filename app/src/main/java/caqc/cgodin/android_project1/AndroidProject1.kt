package caqc.cgodin.android_project1

import android.app.Application
import android.content.Context

class AndroidProject1 : Application() {

    companion object {
        var context: Context? = null
            private set
    }

    override fun onCreate() {
        super.onCreate()
        AndroidProject1.context = applicationContext
    }
}