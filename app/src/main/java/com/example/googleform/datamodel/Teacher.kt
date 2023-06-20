package com.example.googleform.datamodel

import java.io.Serializable

data class Teacher(
    var id: String = "",
    var name: String = "",
    var email: String = "",
    var course: String = "") : Serializable
