package com.example.googleform.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.googleform.R
import com.example.googleform.adapter.TeacherFormAdapter
import com.example.googleform.databinding.FragmentHomeBinding
import com.example.googleform.responsehandling.ResultOf
import com.example.googleform.utils.Extension.gone
import com.example.googleform.utils.Extension.showToast
import com.example.googleform.utils.Extension.visible
import com.example.googleform.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel : HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getTeacherFormData()

        with(binding) {

            val adapter = TeacherFormAdapter(requireContext(), { teacher->

                val action = HomeFragmentDirections.actionHomeFragmentToEditTeacherFormFragment(teacher)
                findNavController().navigate(action)

            }, { teacherId->
                viewModel.deleteTeacherData(teacherId)
            })

            rvTeacherForm.adapter = adapter

            viewModel.teacherFormDataResult.observe(viewLifecycleOwner) {

                when (it) {
                    is ResultOf.Success -> {
                        progressBar.gone()
                        tvEmpty.gone()
                        adapter.populateFormData(it.data)
                    }

                    is ResultOf.Error -> {
                        progressBar.gone()
                        tvEmpty.visible()
                        it.throwable?.localizedMessage?.let { error ->
                            requireContext().showToast(error) }
                    }

                    is ResultOf.Loading -> {
                        progressBar.visible()
                    }
                }
            }

            viewModel.deleteTeacherDataResult.observe(viewLifecycleOwner) {

                when (it) {
                    is ResultOf.Success -> {
                        progressBar.gone()
                        requireContext().showToast(it.data)
                    }

                    is ResultOf.Error -> {
                        progressBar.gone()
                        requireContext().showToast(it.errorMessage)
                    }

                    is ResultOf.Loading -> {
                        progressBar.visible()
                    }
                }
            }

            btnAddForm.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_addTeacherFormFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}