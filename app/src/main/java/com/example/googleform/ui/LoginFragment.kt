package com.example.googleform.ui

import android.graphics.Paint
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.googleform.R
import com.example.googleform.databinding.FragmentLoginBinding
import com.example.googleform.responsehandling.ResultOf
import com.example.googleform.utils.Extension.gone
import com.example.googleform.utils.Extension.showToast
import com.example.googleform.utils.Extension.visible
import com.example.googleform.viewmodel.LoginViewModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            tvRegisterNow.paintFlags = tvRegisterNow.paintFlags or Paint.UNDERLINE_TEXT_FLAG

            tvRegisterNow.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
            }

            btnLogin.setOnClickListener {
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()
                if (validate(email, password)) {
                    viewModel.loginUser(email, password)
                }
            }

            viewModel.userLoginResult.observe(viewLifecycleOwner) { resultOf->

                when (resultOf) {
                    is ResultOf.Success -> {
                        progressBar.gone()
                        requireContext().showToast(resultOf.data)
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
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

    private fun validate(email: String, password: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return if (email.isEmpty()) {
            requireContext().showToast("Enter your email")
            false
        } else if (!pattern.matcher(email).matches()) {
            requireContext().showToast("Enter valid email address")
            false
        } else if (password.isEmpty()) {
            requireContext().showToast("Enter password")
            false
        } else if (password.length < 8) {
            requireContext().showToast("Password must be 4 character long")
            false
        } else
            true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}