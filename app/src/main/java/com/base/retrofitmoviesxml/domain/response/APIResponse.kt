package com.base.retrofitmoviesxml.domain.response

sealed class APIResponse<out T> {
    data class Success<T>(val data: T) : APIResponse<T>()
    data class Error(val code: Int, val message: String) : APIResponse<Nothing>()
    object Loading : APIResponse<Nothing>()
}