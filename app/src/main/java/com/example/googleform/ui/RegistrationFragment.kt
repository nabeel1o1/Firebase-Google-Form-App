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
import com.example.googleform.databinding.FragmentRegistrationBinding
import com.example.googleform.responsehandling.ResultOf
import com.example.googleform.utils.Extension.gone
import com.example.googleform.utils.Extension.showToast
import com.example.googleform.utils.Extension.visible
import com.example.googleform.viewmodel.RegistrationViewModel

class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegistrationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            btnRegister.setOnClickListener {
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()
                val confirmPassword = edtCPassword.text.toString()
                if (validate(email, password, confirmPassword)) {
                    viewModel.registerUser(email, password)
                }
            }

            viewModel.userRegistrationResult.observe(viewLifecycleOwner) { resultOf->

                when (resultOf) {
                    is ResultOf.Success -> {
                        progressBar.gone()
                        requireContext().showToast(resultOf.data)
                        findNavController().navigate(R.id.action_registrationFragment_to_homeFragment)
                    }

                    is ResultOf.Error -> {
                        progressBar.gone()
                        resultOf.throwable!!.localizedMessage?.let { requireContext().showToast(it) }
                    }

                    is ResultOf.Loading -> {
                        progressBar.visible()
                    }
                }
            }
        }
    }

    private fun validate(email: String, password: String, confirmPassword: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        if (email.isEmpty()) {
            requireContext().showToast("Enter your email")
            return false
        } else if (!pattern.matcher(email).matches()) {
            requireContext().showToast("Enter valid email address")
            return false
        } else if (password.isEmpty()) {
            requireContext().showToast("Enter password")
            return false
        } else if (password.length < 4) {
            requireContext().showToast("Password must be 4 character long")
            return false
        } else if (confirmPassword.isEmpty()) {
            requireContext().showToast("Enter password to confirm")
            return false
        } else if (password != confirmPassword) {
            requireContext().showToast("Password doesn't match")
            return false
        } else
            return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}