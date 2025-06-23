package com.ckatsidzira.sipshappen.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import timber.log.Timber

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(message: String, data: T? = null): Resource<T>(data, message)
    class Loading<T>(val isLoading: Boolean = true): Resource<T>(null)
}

inline fun <T> safeFlow(crossinline block: suspend () -> T): Flow<Resource<T>> = flow {
    emit(Resource.Loading())
    emit(Resource.Success(block()))
}.catch { e ->
    Timber.tag("Resource::safeFlow").e(e.localizedMessage)
    emit(Resource.Error(message = "Something went wrong."))
}

suspend inline fun <T> safeCall(crossinline block: suspend () -> T): Resource<T> {
    return try {
        Resource.Success(block())
    } catch (e: Exception) {
        Timber.tag("Resource::safeCall").e(e.localizedMessage)
        Resource.Error("Something went wrong.")
    }
}