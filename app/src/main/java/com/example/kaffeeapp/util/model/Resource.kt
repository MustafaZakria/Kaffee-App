package com.example.kaffeeapp.util.model

import kotlin.Exception

sealed class Resource<T>(val data: T?, val exception: Exception?) {
    class Loading<T> : Resource<T>(null, null)
    class Success<T>(data: T?) : Resource<T>(data, null)
    class Failure<T>(e: Exception?) : Resource<T>(null, e)
}