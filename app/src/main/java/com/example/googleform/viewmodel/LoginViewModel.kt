package com.example.googleform.viewmodel

import androidx.lifecycle.ViewModel
import com.example.googleform.repository.LoginRepo

class LoginViewModel: ViewModel() {

    private val repository = LoginRepo()

    val userLoginResult get() = repository.userLoginResult

    fun loginUser(email: String, password: String) {
        repository.loginUser(email, password)
    }
}