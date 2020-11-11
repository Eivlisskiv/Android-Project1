package caqc.cgodin.android_project1.sqlite

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import caqc.cgodin.android_project1.AndroidProject1
import caqc.cgodin.android_project1.sqlite.models.User
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.isSubclassOf


class DatabaseHandler(name: String, vararg ttables: KClass<*>) : SQLiteOpenHelper(
    AndroidProject1.context,
    name,
    null,
    1
) {

    companion object{
        var database: DatabaseHandler = DatabaseHandler(
            "ProjectDatabase",
            User::class
        )
        private set;
    }

    var tables = ttables;

    override fun onCreate(db: SQLiteDatabase?) {
        //Get and join classes' create table sql query
        val query = tables.map { it ->
            //if is extends from SqlEntity
            if (it.isSubclassOf(SqlEntity::class)) SqlEntity.toSqlTable(it)
            else ""
        }.joinToString(separator = "\n")

        Log.i("Query", query)

        db?.execSQL(query);

        //Update values
        database = this
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val query = tables.map { "drop table if exist ${it.simpleName}" }.joinToString(separator = "\n")
        Log.i("Query", query)
        db?.execSQL(query);
        onCreate(db);
    }

    fun <T> usingDB(action: (db: SQLiteDatabase) -> T): T{
        val db = this.writableDatabase
        val r = action(db)
        db.close();
        return r;
    }

    fun query(sqlQuery: String){
        Log.i("Query", sqlQuery)
        usingDB{ it.execSQL(sqlQuery) }
    }

    fun rawquery(sqlQuery: String){
        Log.i("Query", sqlQuery)
        usingDB{ it.rawQuery(sqlQuery, null) }
    }

    fun <T : SqlEntity> query(clazz: KClass<T>, sqlQuery: String): List<T>{
        return usingDB {
            val cursor = it.rawQuery(sqlQuery, null);
            val entries : ArrayList<T> = arrayListOf()
            if(cursor.moveToFirst()){
                cursor.moveToFirst()
                while (!cursor.isAfterLast()){
                    val v = clazz.createInstance()
                    v.setWithCursor(clazz, cursor);
                    entries.add(v);
                    cursor.moveToNext()
                }
            }
            entries;
        }
    }


    fun <T : SqlEntity> insert(entry: T){
        usingDB { db -> db.insert(entry.tableName(), null, entry.contentValues()) }
    }
}