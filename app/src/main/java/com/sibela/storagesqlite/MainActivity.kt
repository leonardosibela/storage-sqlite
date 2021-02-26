package com.sibela.storagesqlite

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.editButton

class MainActivity : AppCompatActivity() , TaskAdapter.Callback {

    private var selectedTask: Task? = null
    private lateinit var adapter: TaskAdapter
    private lateinit var taskDao: TaskDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        taskDao = TaskDaoSqlite(this)

        setupRecyclerView()
        saveButton.setOnClickListener { onSaveClicked() }
        editButton.setOnClickListener { onEditClicked() }
    }

    override fun onEditClicked(task: Task) {
        displayEditMode()
        taskInput.setText(task.name)
        this.selectedTask = task
    }

    private fun displayEditMode() {
        editButton.visibility = View.VISIBLE
        saveButton.visibility = View.INVISIBLE
    }

    private fun displaySaveMode() {
        editButton.visibility = View.INVISIBLE
        saveButton.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        adapter = TaskAdapter(callback = this)
        val tasks = arrayListOf<Task>()
        tasks.addAll(taskDao.fetchAll())
        adapter.setTaskArray(tasks)
        taskRecycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    private fun onSaveClicked() {
        val task = Task(name = taskInput.text.toString())
        saveTask(task)
    }

    private fun clearForm() {
        taskInput.setText("")
    }

    private fun onEditClicked() {
        val editedTask = Task(selectedTask!!.id, taskInput.text.toString())
        editTask(editedTask)
        selectedTask = null
    }

    override fun onDeleteClicked(task: Task) {
        val removed = taskDao.remove(task)
        if (removed) {
            val tasksArray = arrayListOf<Task>()
            tasksArray.addAll(taskDao.fetchAll())
            adapter.setTaskArray(tasksArray)
        }
    }

    private fun saveTask(task: Task) {
        val inserted = taskDao.insert(task)
        if (inserted) {
            val tasksArray = arrayListOf<Task>()
            tasksArray.addAll(taskDao.fetchAll())
            adapter.setTaskArray(tasksArray)
            clearForm()
        }
    }

    private fun editTask(task: Task) {
        val updated = taskDao.update(task)
        if (updated) {
            val tasksArray = arrayListOf<Task>()
            tasksArray.addAll(taskDao.fetchAll())
            adapter.setTaskArray(tasksArray)
            clearForm()
            displaySaveMode()
        }
    }
}