package com.example.googleform.viewmodel

import androidx.lifecycle.ViewModel
import com.example.googleform.datamodel.Teacher
import com.example.googleform.repository.EditFormRepo

class EditFormViewModel : ViewModel() {

    private val repository = EditFormRepo()

    val teacherFormDataResult get() = repository.teacherFormDataResult

    fun editTeacherForm(teacher: Teacher) {
        repository.editTeacherForm(teacher)
    }
}