package com.example.googleform.viewmodel

import androidx.lifecycle.ViewModel
import com.example.googleform.repository.HomeRepo

class HomeViewModel : ViewModel() {

    private val repository = HomeRepo()

    val teacherFormDataResult get() = repository.teacherFormDataResult

    val deleteTeacherDataResult get() = repository.deleteTeacherResult

    fun getTeacherFormData() {
        repository.getTeacherFormData()
    }

    fun deleteTeacherData(teacherId: String) {
        repository.deleteTeacherForm(teacherId)
    }
}