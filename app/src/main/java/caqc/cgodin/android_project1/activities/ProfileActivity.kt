package caqc.cgodin.android_project1.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import caqc.cgodin.android_project1.R
import caqc.cgodin.android_project1.sqlite.DatabaseHandler
import caqc.cgodin.android_project1.sqlite.models.Restaurant

class ProfileActivity : ActivityExtension(R.id.profileToolbar) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val res = Restaurant("someguy@wtv.com","nom", "adresse", "https://logos-world.net/wp-content/uploads/2020/04/McDonalds-Logo-700x394.png", "0.0", "0.0")
        DatabaseHandler.database.insert(res)
        initRecyclerView(this)
        addDataSet()
    }
}