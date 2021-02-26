package com.sibela.storagesqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseManager(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "books.db"
        private const val DATABASE_VERSION = 1

        const val TASK_TABLE = "TASK"
        const val TASK_ID = "id"
        const val TASK_NAME = "name"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val taskTable =
            "CREATE TABLE $TASK_TABLE ($TASK_ID INTEGER PRIMARY KEY AUTOINCREMENT, $TASK_NAME TEXT NOT NULL)"

        db.execSQL(taskTable)

        db.execSQL("insert into $TASK_TABLE values (0, 'Buy groceries');")
        db.execSQL("insert into $TASK_TABLE values (1, 'Meditate with Sarah');")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}
}