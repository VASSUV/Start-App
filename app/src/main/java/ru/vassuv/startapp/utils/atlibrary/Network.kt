package ru.vassuv.startapp.utils.atlibrary

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import ru.vassuv.startapp.utils.atlibrary.json.JsonObject
import java.net.UnknownHostException


data class Response<T>(val result: T?,
                       val error: Error = NO_ERROR)

data class Error(val status: Int = 0,
                 override val message: String? = null,
                 val errorBody: String? = null) : Exception()

val NO_ERROR = Error()
val ERROR_EMPTY = Error(1001)
val ERROR_INTERNET_NOT_FOUND = Error(800, "Отсутсвует интернет соединение")
val ERROR_TIMEOUT = Error(900, "Превышено время ожидания")
val ERROR = Error(1000, "Произошла ошибка")

fun Throwable.toError() = when (this) {
    is UnknownHostException -> ERROR_INTERNET_NOT_FOUND
    is Error -> this
    else -> ERROR
}

suspend fun get(host: String, params: Map<String, String>): String? = RETROFIT.get(host, params).await()

suspend fun <T> getWithMap(host: String,
                           params: Map<String, String>,
                           postDelay: suspend (String) -> T?
): T? = postDelay(RETROFIT.get(host, params).await())

suspend fun post(host: String, params: Map<String, String>): String? = RETROFIT.post(host, params).await()

suspend fun <T> postWithMap(host: String,
                            params: Map<String, String>,
                            postDelay: suspend (String) -> T?
): T? = postDelay(RETROFIT.post(host, params).await())

interface ApiMethod {

    val host: String

    suspend fun responseJson(params: Map<String, String> = hashMapOf(),
                             isCheckError: Boolean = false
    ): Response<JsonObject> = responseWithMap(params, { response ->
        JsonObject.readFrom(response).apply {
            if (isCheckError) {
                val status = this.int("status") ?: 0
                if (status > 0) throw Error(status, this.string("message"), this["meta"].toString())
            }
        }
    })

    suspend fun response(params: Map<String, String> = hashMapOf()
    ): Response<String> = responseWithMap(params, { it })

    suspend fun <T> responseWithMap(params: Map<String, String> = hashMapOf(),
                                    postDelay: suspend (String) -> T?
    ): Response<T> {
        var result: Response<T>? = null
        val job = launch(CommonPool) {
            result = try {
                Response(getWithMap(host, params, postDelay))
            } catch (throwable: Throwable) {
                Response(null, throwable.toError())
            }
        }

        delay(1000)
        job.join()
        return result ?: Response<T>(null, ERROR_EMPTY)
    }
}
