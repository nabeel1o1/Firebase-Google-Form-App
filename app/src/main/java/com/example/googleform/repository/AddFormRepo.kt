package com.example.googleform.repository

import androidx.lifecycle.MutableLiveData
import com.example.googleform.datamodel.Teacher
import com.example.googleform.responsehandling.ResultOf
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AddFormRepo {

    val databaseReference = FirebaseDatabase.getInstance().getReference("teachers")

    private val _teacherFormDataResult = MutableLiveData<ResultOf<String>>()
    val teacherFormDataResult get() = _teacherFormDataResult

    fun addTeacherForm(teacher: Teacher) {

        _teacherFormDataResult.postValue(ResultOf.Loading)

        val newTeacherReference = databaseReference.push()
        newTeacherReference.setValue(teacher)
            .addOnCompleteListener {
                _teacherFormDataResult.postValue(ResultOf.Success("Form added"))
            }
            .addOnFailureListener {
                _teacherFormDataResult.postValue(it.localizedMessage?.let { it1 ->
                    ResultOf.Error(
                        it1, it)
                })
            }
    }
}