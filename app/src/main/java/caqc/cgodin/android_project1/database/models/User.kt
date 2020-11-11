package caqc.cgodin.android_project1.database.models

import android.database.Cursor
import androidx.room.*
import caqc.cgodin.android_project1.database.EntityDatabase

@Entity(tableName = "users")
public class User(email: String, password:String, username: String) {

    companion object{
        fun getUser(email:String) : User? {
            val db = EntityDatabase.getDatabase<User>("User")
            val result = db.getQuery<User>("select * from users where email = ?", email as Object);
            return result.value?.first()
        }
    }

    @PrimaryKey(autoGenerate = false)
    val email: String = email

    @ColumnInfo(name = "password")
    val password: String = password;

    @ColumnInfo(name = "username")
    val username: String = username;

}