package com.example.googleform.viewmodel

import androidx.lifecycle.ViewModel
import com.example.googleform.datamodel.Teacher
import com.example.googleform.repository.AddFormRepo

class AddFormViewModel : ViewModel() {

    private val repository = AddFormRepo()

    val teacherFormDataResult get() = repository.teacherFormDataResult

    fun addTeacherForm(teacher: Teacher) {
        repository.addTeacherForm(teacher)
    }
}