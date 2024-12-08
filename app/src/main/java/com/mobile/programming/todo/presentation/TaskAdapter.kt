package com.mobile.programming.todo.presentation

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobile.programming.todo.data.Task
import com.mobile.programming.todo.databinding.ItemTaskBinding

class TaskAdapter(
    private val onTaskCheckedChange: (Task) -> Unit // 체크박스 상태 변경을 처리하기 위한 콜백
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onTaskCheckedChange)
    }

    class TaskViewHolder private constructor(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Task, onTaskCheckedChange: (Task) -> Unit) {
            binding.taskTitle.text = item.title
            binding.taskDescription.text = item.description
            print("imgrUL: ${item.imgUri}")
            if (item.imgUri != null) {
                binding.ivTask.setImageBitmap(item.imgUri)
            } else {
                binding.ivTask.visibility = android.view.View.GONE
            }

            // 체크박스 상태 반영
            binding.taskCheckBox.isChecked = item.isCompleted
            applyStrikeThrough(binding.taskTitle, item.isCompleted)

            // 체크박스 상태 변경 이벤트 처리
            binding.taskCheckBox.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
                item.isCompleted = isChecked
                applyStrikeThrough(binding.taskTitle, isChecked)
                onTaskCheckedChange(item) // Task 상태 변경 콜백 호출
            }
        }

        private fun applyStrikeThrough(textView: android.widget.TextView, isStriked: Boolean) {
            textView.paintFlags = if (isStriked) {
                textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }

        companion object {
            fun from(parent: ViewGroup): TaskViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemTaskBinding.inflate(layoutInflater, parent, false)
                return TaskViewHolder(binding)
            }
        }
    }
}

class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
}
