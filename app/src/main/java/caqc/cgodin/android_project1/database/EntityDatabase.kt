package caqc.cgodin.android_project1.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import caqc.cgodin.android_project1.database.models.Restaurant
import caqc.cgodin.android_project1.database.models.User
import java.util.*

@Database(entities = [User::class, Restaurant::class], version = 1)
abstract class EntityDatabase<T> : RoomDatabase()  {

    abstract fun dao(): ModelDao<T>;

    companion object {
        //HashMap of databases where the key is the model name
        @Volatile
        lateinit var databases: HashMap<String, EntityDatabase<*>>;

        fun <T> getDatabase(context: Context, key:String) : EntityDatabase<T> {
            return (databases[key] ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EntityDatabase::class.java,
                    key ).build()
                databases[key] = instance
                // return instance
                instance
            }) as EntityDatabase<T>
        }
    }
}