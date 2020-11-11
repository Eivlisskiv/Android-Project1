package caqc.cgodin.android_project1.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Restaurant(key:Int, name:String) {

    @PrimaryKey(autoGenerate = true)
    val key:Int = key;

    @ColumnInfo(name = "name")
    val name: String = name;
}