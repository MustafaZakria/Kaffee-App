package com.example.kaffeeapp.model

sealed class Resource<T>(val data: T?, val message: String?) {
    class Loading<T> : Resource<T>(null, null)
    class Success<T>(data: T) : Resource<T>(data, null)
    class Failure<T>(message: String) : Resource<T>(null, message)
}