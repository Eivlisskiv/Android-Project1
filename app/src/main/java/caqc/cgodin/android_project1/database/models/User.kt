package caqc.cgodin.android_project1.database.models

import androidx.room.*

@Entity
class User(email: String, pwd:String) {

    @PrimaryKey(autoGenerate = false)
    val email: String = email

    @ColumnInfo(name = "password")
    val password: String = pwd;
}