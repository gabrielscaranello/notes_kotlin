package com.example.notes_kotlin

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast

class DbManager {

    var dbName = "MyNotes"

    var dbTable = "notes"

    var colId = "id"
    var colTitle = "title"
    var colDesc = "description"

    var dbVersion = 1

    val sqlCreateTable =  "CREATE TABLE IF NOT EXISTS " + dbTable +"("+colId+" INTEGER PRIMARY KEY," + colTitle + " TEXT, " + colDesc + " TEXT)"

    var sqlDB:SQLiteDatabase?=null

    constructor(context: Context) {
        var db = DatabaseHelperNotes(context)
        sqlDB = db.writableDatabase
    }

    inner class DatabaseHelperNotes : SQLiteOpenHelper {
        var context:Context?=null

        constructor(context: Context): super(context, dbName, null, dbVersion) {
            this.context = context
        }

        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(sqlCreateTable)
            Toast.makeText(this.context, "database created ...", Toast.LENGTH_SHORT).show()
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("DROP TABLE IF EXISTS " + dbTable)
        }
    }

    fun insert(values: ContentValues):Long{
        val id = sqlDB!!.insert(dbTable, "", values)
        return id
    }

    fun Query(projection:Array<String>, selection:String, selectionArgs:Array<String>, sorOrder:String) : Cursor{
        val qb = SQLiteQueryBuilder()
        qb.tables = dbTable
        val cursor = qb.query(sqlDB, projection, selection, selectionArgs, null, null, sorOrder)
        return cursor
    }

    fun delete(selection: String, selectionArgs: Array<String>):Int {
        val count = sqlDB!!.delete(dbTable, selection, selectionArgs)
        return count
    }

    fun upate(values: ContentValues, selection: String, selectionArgs: Array<String>):Int {
        val count = sqlDB!!.update(dbTable, values, selection, selectionArgs)
        return count
    }
}