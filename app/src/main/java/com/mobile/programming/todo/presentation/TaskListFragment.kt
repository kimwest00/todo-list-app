package com.mobile.programming.todo.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.programming.todo.R
import com.mobile.programming.todo.data.Task
import com.mobile.programming.todo.databinding.FragmentTaskListBinding

class TaskListFragment : Fragment() {

    private lateinit var binding: FragmentTaskListBinding
    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Adapter 초기화 및 설정
        val adapter = TaskAdapter { task ->
            // Task의 완료 상태를 업데이트
            val updatedTask = task.copy(isCompleted = !task.isCompleted)
            taskViewModel.update(updatedTask)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        // ViewModel의 LiveData를 관찰
        taskViewModel.allTasks.observe(viewLifecycleOwner, Observer { tasks ->
            tasks?.let {
                adapter.submitList(it)
            }
        })

        // Add 버튼 클릭 이벤트 설정
        binding.addButton.setOnClickListener {
            findNavController().navigate(R.id.action_taskListFragment_to_addTaskFragment)
        }
    }
}
