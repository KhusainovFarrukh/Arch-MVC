package kh.farrukh.arch_mvc.utils

import retrofit2.HttpException
import retrofit2.Response

/**
 *Created by farrukh_kh on 4/6/22 9:12 PM
 *kh.farrukh.arch_mvc.utils
 **/
fun <T> Response<T>.toResult(): Result<T> {
    body().let { body ->
        return if (isSuccessful && body != null) Result.success(body)
        else Result.failure(HttpException(this))
    }
}

fun <T> Result<T>.handle(onSuccess: SingleBlock<T>, onError: SingleBlock<Throwable?>) {
    getOrNull().let { data ->
        if (data != null) onSuccess(data)
        else onError(exceptionOrNull())
    }
}