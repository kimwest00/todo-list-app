package com.mobile.programming.todo.presentation

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mobile.programming.todo.R
import com.mobile.programming.todo.data.Task
import com.mobile.programming.todo.databinding.FragmentAddTaskBinding
import com.mobile.programming.todo.util.convertUriToBitmap

class AddTaskFragment : Fragment() {

    private lateinit var binding: FragmentAddTaskBinding
    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
        observeData()

    }
    private fun setView(){
        binding.ivTask.setOnClickListener {
            getContent.launch("image/*")
        }
        binding.btnSave.setOnClickListener {
            val task = Task(
                title = binding.editTextTaskTitle.text.toString(),
                description = binding.editTextTaskDescription.text.toString(),
                imgUri = bitmap
            )
            taskViewModel.insertTask(task)
            findNavController().navigate(R.id.action_addTaskFragment_to_taskListFragment)
        }
    }

    private var insertValue = emptyList<Long>()
    private var bitmap: Bitmap? = null

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                Log.d("ADD", "uri: $uri")
                binding.ivTask.setImageURI(uri)
                context?.let { it1 ->
                    var tmpBitmap = convertUriToBitmap(uri, it1)
                    tmpBitmap?.let { it2 -> bitmap = it2 }
                }

            }
        }

    private fun observeData() {
        taskViewModel.insertResult.observe(viewLifecycleOwner) {
            insertValue = it
        }

    }
}
