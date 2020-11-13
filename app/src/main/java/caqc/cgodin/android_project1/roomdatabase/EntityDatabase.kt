package caqc.cgodin.android_project1.roomdatabase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import caqc.cgodin.android_project1.AndroidProject1
import caqc.cgodin.android_project1.roomdatabase.models.Restaurant
import caqc.cgodin.android_project1.roomdatabase.models.User
import kotlin.collections.HashMap

@Database(entities = [User::class, Restaurant::class], version = 1, exportSchema = false)
abstract class EntityDatabase : RoomDatabase()  {

    abstract fun dao(): ModelDao;

    companion object {
        //HashMap of databases where the key is the model name
        @Volatile
        var databases: HashMap<String, EntityDatabase> = HashMap();

        fun <T> getDatabase(key:String) : EntityDatabase {
            return (databases[key] ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    AndroidProject1.context?.applicationContext
                        ?: throw Exception("Invalid app context"),
                    EntityDatabase::class.java,
                    key ).build()
                databases[key] = instance
                // return instance
                instance
            })
        }
    }

    fun <T> getQuery(query:String, vararg args:Any) : LiveData<List<T>> {
        return dao().rawQuery( if(args.isNotEmpty()) SimpleSQLiteQuery(query, args)
        else SimpleSQLiteQuery(query)) as LiveData<List<T>>
    }
}