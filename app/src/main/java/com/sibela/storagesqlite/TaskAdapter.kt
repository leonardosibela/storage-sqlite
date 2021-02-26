package com.sibela.storagesqlite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.task_item.view.*

class TaskAdapter(
    var tasks: ArrayList<Task> = arrayListOf(),
    val callback: Callback
) : RecyclerView.Adapter<TaskAdapter.Holder>() {

    fun update(task: Task) {
        val foundTask = tasks.find { it.id == task.id }
        if (foundTask != null) {
            val index = tasks.indexOf(foundTask)
            tasks.removeAt(index)
            tasks.add(index, task)
            notifyItemChanged(index)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.task_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val task = tasks[position]
        holder.bind(task)
    }

    override fun getItemCount() = tasks.size

    fun setTaskArray(tasks: ArrayList<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {

        private val taskName: TextView = view.taskNameText
        private val deleteButton: ImageView = view.deleteButton
        private val editButton: ImageView = view.editButton

        fun bind(task: Task) {
            taskName.text = task.name

            deleteButton.setOnClickListener { callback.onDeleteClicked(task) }
            editButton.setOnClickListener { callback.onEditClicked(task) }
        }
    }

    interface Callback {
        fun onEditClicked(task: Task)
        fun onDeleteClicked(task: Task)
    }
}