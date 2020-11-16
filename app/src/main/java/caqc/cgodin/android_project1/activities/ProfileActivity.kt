package caqc.cgodin.android_project1.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import caqc.cgodin.android_project1.R

class ProfileActivity : ActivityExtension(R.id.profileToolbar) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
    }
}