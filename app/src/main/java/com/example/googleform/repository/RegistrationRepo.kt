package com.example.googleform.repository

import androidx.lifecycle.MutableLiveData
import com.example.googleform.responsehandling.ResultOf
import com.google.firebase.auth.FirebaseAuth

class RegistrationRepo {

    private val firebaseAuth = FirebaseAuth.getInstance()

    private val _userRegistrationResult = MutableLiveData<ResultOf<String>>()
    val userRegistrationResult get() = _userRegistrationResult

    fun registerUser(email: String, password: String) {

        _userRegistrationResult.postValue(ResultOf.Loading)

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task->
                if (task.isSuccessful) {
                    _userRegistrationResult.postValue(ResultOf.Success("Registration successful"))
                } else {
                    _userRegistrationResult.postValue(ResultOf.
                    Error("Something went wrong!", task.exception))
                }
            }
    }

}