package com.example.googleform.viewmodel

import androidx.lifecycle.ViewModel
import com.example.googleform.repository.RegistrationRepo

class RegistrationViewModel: ViewModel() {

    private val repository = RegistrationRepo()

    val userRegistrationResult get() = repository.userRegistrationResult

    fun registerUser(email: String, password: String) {
        repository.registerUser(email, password)
    }
}