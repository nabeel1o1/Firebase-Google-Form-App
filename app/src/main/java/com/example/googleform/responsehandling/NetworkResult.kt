package com.example.googleform.responsehandling

sealed class ResultOf<out T: Any> {

    data class Success<out T: Any> (val data: T) : ResultOf<T>()
    data class Error(val errorMessage: String, val throwable: Throwable?) : ResultOf<Nothing>()
    object Loading: ResultOf<Nothing>()

}