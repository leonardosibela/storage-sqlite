package com.sibela.storagesqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() , TaskAdapter.Callback {

    private var selectedTask: Task? = null
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        saveButton.setOnClickListener { onSaveClicked() }
        editButton.setOnClickListener { onEditClicked() }
    }

    override fun onEditClicked(task: Task) {
        editButton.visibility = View.VISIBLE
        saveButton.visibility = View.INVISIBLE
        taskInput.setText(task.name)
        this.selectedTask = task
    }

    private fun setupRecyclerView() {
        adapter = TaskAdapter(callback = this)
        val tasks = arrayListOf<Task>()
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

    private fun onEditClicked() {
        val editedTask = Task(selectedTask!!.id, taskInput.text.toString())
        editTask(editedTask)
        selectedTask = null
    }

    override fun onDeleteClicked(task: Task) {

    }

    private fun saveTask(task: Task) {

    }

    private fun editTask(task: Task) {

    }
}