package com.example.googleform.repository

import androidx.lifecycle.MutableLiveData
import com.example.googleform.responsehandling.ResultOf
import com.google.firebase.auth.FirebaseAuth

class LoginRepo {

    private val firebaseAuth = FirebaseAuth.getInstance()

    private val _userLoginResult = MutableLiveData<ResultOf<String>>()
    val userLoginResult get() = _userLoginResult

    fun loginUser(email: String, password: String) {

        _userLoginResult.postValue(ResultOf.Loading)

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task->
                if (task.isSuccessful) {
                    _userLoginResult.postValue(ResultOf.Success("Successfully login"))
                } else {
                    _userLoginResult.postValue(ResultOf.
                    Error("Something went wrong!", task.exception))
                }
            }
    }
}