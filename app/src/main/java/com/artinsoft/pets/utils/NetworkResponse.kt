package com.artinsoft.pets.utils

sealed class NetworkResponse<out T> {
    data class Success<out T>(
        val data: T
    ): NetworkResponse<T>()

    data class Failure(
        val e: Exception
    ): NetworkResponse<Nothing>()
}