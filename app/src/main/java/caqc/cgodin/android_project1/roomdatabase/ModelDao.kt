package caqc.cgodin.android_project1.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import java.util.*

@Dao
interface ModelDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addElement(element: Objects);

    @RawQuery
    fun rawQuery(query: SupportSQLiteQuery) : LiveData<List<Objects>>;
}