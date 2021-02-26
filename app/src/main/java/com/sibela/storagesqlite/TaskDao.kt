package com.sibela.storagesqlite

interface TaskDao {

    fun open()

    fun close()

    fun fetchAll(): List<Task>

    fun getById(id: Long): Task

    fun insert(task: Task): Boolean

    fun remove(task: Task): Boolean

    fun update(task: Task): Boolean

}