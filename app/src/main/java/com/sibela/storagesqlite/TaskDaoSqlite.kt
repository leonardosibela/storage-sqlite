package com.sibela.storagesqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.sibela.storagesqlite.DatabaseManager.Companion.TASK_TABLE
import com.sibela.storagesqlite.DatabaseManager.Companion.TASK_ID
import com.sibela.storagesqlite.DatabaseManager.Companion.TASK_NAME

class TaskDaoSqlite(context: Context) : TaskDao {

    private val dbManager = DatabaseManager(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        val ALL_COLUMNS = arrayOf(TASK_ID, TASK_NAME)
    }

    override fun open() {
        database = dbManager.writableDatabase
    }

    override fun close() {
        dbManager.close()
    }

    override fun fetchAll(): List<Task> {
        open()
        val books: ArrayList<Task> = arrayListOf()

        val cursor: Cursor = database.query(
            TASK_TABLE, ALL_COLUMNS, null, null, null, null, null
        )

        cursor.moveToFirst()

        while (!cursor.isAfterLast) {
            val book: Task = cursorToBook(cursor)
            books.add(book)
            cursor.moveToNext()
        }

        cursor.close()
        close()
        return books
    }

    override fun getById(id: Long): Task {
        open()
        val cursor: Cursor = database.query(
            TASK_TABLE, ALL_COLUMNS, idEqualsClause(), arrayOf(id.toString()), null, null, null
        )

        cursor.moveToFirst()
        close()
        return cursorToBook(cursor)
    }

    override fun insert(task: Task): Boolean {
        open()
        val values: ContentValues = taskToContentValuesWithoutId(task)
        val rowId = database.insert(TASK_TABLE, null, values)
        close()
        return rowId > -1
    }

    override fun remove(task: Task): Boolean {
        open()
        val removed = database.delete(TASK_TABLE, idEqualsClause(), arrayOf(task.id.toString()))
        close()
        return removed >= 0
    }

    override fun update(task: Task): Boolean {
        open()
        val values: ContentValues = taskToContentValues(task)
        val rowsAffected =
            database.update(TASK_TABLE, values, idEqualsClause(), arrayOf(task.id.toString()))
        close()
        return rowsAffected > 0
    }

    private fun cursorToBook(cursor: Cursor): Task {
        open()
        val task = Task(cursor.getInt(0), cursor.getString(1))
        close()
        return task
    }

    private fun idEqualsClause() = "$TASK_ID = ? "

    private fun taskToContentValues(task: Task): ContentValues {
        val values = ContentValues()
        values.put(TASK_ID, task.id)
        values.put(TASK_NAME, task.name)
        return values
    }

    private fun taskToContentValuesWithoutId(task: Task): ContentValues {
        val values = ContentValues()
        values.put(TASK_NAME, task.name)
        return values
    }
}