package caqc.cgodin.android_project1.sqlite

import android.content.ContentValues
import android.database.Cursor
import android.util.Log
import caqc.cgodin.android_project1.Utils
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KType
import kotlin.reflect.jvm.jvmName


open class  SqlEntity() {

    companion object{
        fun toSqlTable(clazz: KClass<*>?): String{
            if(clazz == null) return "";
            return """
                CREATE TABLE ${clazz.simpleName} (
                    ${fieldsToColumns(clazz).joinToString()}
                );
            """.trimIndent()
        }

        fun fieldsToColumns(clazz: KClass<*>) : List<String> {
            return Utils.getFields(clazz).map { it -> "${it.name} ${fieldTypeToColumn(it.returnType)}" }
        }

        fun fieldTypeToColumn(type: KType) : String{
            var t = type.toString()
            t = t.substring(t.indexOf('.') + 1).trimEnd('?')
            return when(t){
                "Int", "Double",
                    -> "number"
                else -> "varchar(255)"
            }
        }
    }

    private var clazz : KClass<*>? = null;

    constructor(c: KClass<*>) : this() {
        clazz = c;
    }

    fun tableName(): String? {
        return clazz?.simpleName;
    }

    fun toSqlTable() : String? {
        return SqlEntity.toSqlTable(clazz);
    }

    fun toEntryQuery(): String {
        if(clazz == null) return "";
        return """
            insert into ${clazz!!.simpleName} (${Utils.getFields(clazz!!).joinToString{ it.name }})
            values (${Utils.getFields(clazz!!).map { it.call(this) }.joinToString(
            prefix = "'",
            postfix = "'",
            separator = "', '"
        )})
        """.trimIndent()
    }

    fun contentValues(): ContentValues? {
        if(clazz == null) return null;
        val values = ContentValues()
        Utils.getFields(clazz!!).forEach{
            val v = it.call(this).toString()
            Log.i("App", v.toString())
            values.put(it.name, v)
        }
        return values;
    }

    fun <T : SqlEntity> setWithCursor(c: KClass<T>, cursor: Cursor) {
        clazz = c;
        Utils.getFields(clazz as KClass<*>).forEach{
            it.setter.call(this, getColumnData(it, cursor))
        }
    }

    fun <T> getColumnData(prop : KMutableProperty<T>, cursor: Cursor) : T{
        val index = cursor.getColumnIndex(prop.name)
        var t = prop.returnType.toString()
        t = t.substring(t.indexOf('.') + 1).trimEnd('?')
        return when(t){
            "Int" -> cursor.getInt(index);
            "Double" -> cursor.getDouble(index)
            else -> cursor.getString(index);
        } as T;
    }
}