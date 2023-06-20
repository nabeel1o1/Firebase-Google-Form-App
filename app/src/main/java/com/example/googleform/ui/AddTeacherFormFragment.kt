package com.example.googleform.ui

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.googleform.R
import com.example.googleform.databinding.FragmentAddTeacherFormBinding
import com.example.googleform.datamodel.Teacher
import com.example.googleform.responsehandling.ResultOf
import com.example.googleform.utils.Extension.gone
import com.example.googleform.utils.Extension.showToast
import com.example.googleform.utils.Extension.visible
import com.example.googleform.viewmodel.AddFormViewModel

class AddTeacherFormFragment : Fragment() {

    private var _binding: FragmentAddTeacherFormBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddFormViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTeacherFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            btnAddForm.setOnClickListener {

                val name = edtName.text.toString()
                val email = edtEmail.text.toString()
                val course = edtCourse.text.toString()

                if (validate(name, email, course)) {
                    val teacher = Teacher(name = name, email = email, course = course)
                    viewModel.addTeacherForm(teacher)
                }
            }

            viewModel.teacherFormDataResult.observe(viewLifecycleOwner) { resultOf->

                when (resultOf) {
                    is ResultOf.Success -> {
                        progressBar.gone()
                        requireContext().showToast(resultOf.data)
                        findNavController().navigate(R.id.action_addTeacherFormFragment_to_homeFragment)
                    }

                    is ResultOf.Error -> {
                        progressBar.gone()
                        requireContext().showToast(resultOf.errorMessage)
                    }

                    is ResultOf.Loading -> {
                        progressBar.visible()
                    }
                }
            }
        }
    }

    private fun validate(name: String, email: String, course: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return if (name.isEmpty()) {
            requireContext().showToast("Enter teacher name")
            false
        } else if (email.isEmpty()) {
            requireContext().showToast("Enter teacher email")
            false
        } else if (!pattern.matcher(email).matches()) {
            requireContext().showToast("Enter valid email address")
            false
        } else if (course.isEmpty()) {
            requireContext().showToast("Enter course")
            false
        } else
            true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}