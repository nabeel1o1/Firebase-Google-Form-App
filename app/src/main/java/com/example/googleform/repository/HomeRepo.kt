package com.example.googleform.repository

import androidx.lifecycle.MutableLiveData
import com.example.googleform.datamodel.Teacher
import com.example.googleform.responsehandling.ResultOf
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeRepo {

    private val databaseReference = FirebaseDatabase.getInstance().reference.child("teachers")

    private val _teacherFormDataResult = MutableLiveData<ResultOf<List<Teacher>>>()
    val teacherFormDataResult get() = _teacherFormDataResult

    private val _deleteTeacherResult = MutableLiveData<ResultOf<String>>()
    val deleteTeacherResult get() = _deleteTeacherResult

    fun getTeacherFormData () {

        _teacherFormDataResult.postValue(ResultOf.Loading)

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val teacherList = mutableListOf<Teacher>()

                for (snapshot in dataSnapshot.children) {
                    val teacher = snapshot.getValue(Teacher::class.java)
                    val teacherId = snapshot.key

                    teacher?.let {

                        val teacherWithId = Teacher(teacherId!!, it.name, it.email, it.course)
                        teacherList.add(teacherWithId)
                    }
                }
                if (teacherList.isEmpty()) {
                    _teacherFormDataResult.postValue(ResultOf.
                    Error("No data found", null))
                } else
                    _teacherFormDataResult.postValue(ResultOf.Success(teacherList))
            }

            override fun onCancelled(databaseError: DatabaseError) {
                _teacherFormDataResult.postValue(ResultOf.
                Error(databaseError.message, databaseError.toException()))
            }
        }

        databaseReference.addValueEventListener(valueEventListener)
    }

    fun deleteTeacherForm(teacherId: String) {

        _deleteTeacherResult.postValue(ResultOf.Loading)

        val databaseReference = FirebaseDatabase.getInstance()
            .getReference("teachers/$teacherId")

        databaseReference.removeValue()
            .addOnCompleteListener {
                _deleteTeacherResult.postValue(ResultOf.Success("Successfully deleted"))
            }
            .addOnFailureListener {
                _deleteTeacherResult.postValue(it.localizedMessage?.let {
                        it1 -> ResultOf.Error(it1, it) })
            }

    }
}