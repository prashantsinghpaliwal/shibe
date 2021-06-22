package com.prashant.shibe.common


enum class Status(val value: Int) {
    SUCCESS(1 shl 1), ERROR(1 shl 2), LOADING(1)
}

data class Resource<out T>(val status: Status, val data: T?, val throwable: Throwable?) {

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(throwable: Throwable): Resource<T> {
            return Resource(Status.ERROR, null, throwable)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null, null)
        }
    }


}

fun <T> Resource<T>.render(onLoading: () -> Unit, onSuccess: (data: T?) -> Unit, onError: (Throwable?) -> Unit) {
    when(status) {
        Status.LOADING -> onLoading.invoke()
        Status.ERROR -> onError.invoke(throwable)
        Status.SUCCESS -> onSuccess.invoke(data)
    }
}