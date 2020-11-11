package caqc.cgodin.android_project1.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.sqlite.db.SimpleSQLiteQuery

class EntityViewModel<T>(app: Application, name: String) : AndroidViewModel(app) {

    val name = name;

    init {
        //I'll leave this here in case I have to add more
    }

    fun dao(): ModelDao {
        return EntityDatabase.getDatabase<T>(name).dao();
    }

    fun query(query:String, vararg args:Object) : LiveData<List<T>> {
        return (dao().rawQuery( if(args.isNotEmpty()) SimpleSQLiteQuery(query, args)
        else SimpleSQLiteQuery(query))) as LiveData<List<T>>;
    }
}