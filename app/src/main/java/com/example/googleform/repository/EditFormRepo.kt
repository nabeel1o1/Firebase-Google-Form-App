package com.example.googleform.repository

import androidx.lifecycle.MutableLiveData
import com.example.googleform.datamodel.Teacher
import com.example.googleform.responsehandling.ResultOf
import com.google.firebase.database.FirebaseDatabase

class EditFormRepo {

    private val _teacherFormDataResult = MutableLiveData<ResultOf<String>>()
    val teacherFormDataResult get() = _teacherFormDataResult

    fun editTeacherForm(teacher: Teacher) {

        _teacherFormDataResult.postValue(ResultOf.Loading)

        val databaseReference =
            FirebaseDatabase.getInstance().getReference("teachers/${teacher.id}")

        val updates = hashMapOf<String, Any>(
            "name" to teacher.name,
            "email" to teacher.email,
            "course" to teacher.course
        )

        databaseReference.updateChildren(updates)

            .addOnCompleteListener {
                _teacherFormDataResult.postValue(ResultOf.Success("Edited successfully"))
            }
            .addOnFailureListener {
                _teacherFormDataResult.postValue(it.localizedMessage?.let { it1 ->
                    ResultOf.Error(
                        it1, it)
                })
            }
    }
}