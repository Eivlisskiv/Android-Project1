package caqc.cgodin.android_project1.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface ModelDao<T> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addElement(element: T);

    @RawQuery
    fun rawQuery(query: SupportSQLiteQuery) : LiveData<List<T>>;
}