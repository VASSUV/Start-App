package ru.vassuv.startapp.utils.atlibrary

import kotlinx.coroutines.experimental.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*
import ru.vassuv.startapp.utils.atlibrary.json.JsonObject
import java.net.UnknownHostException
import kotlin.coroutines.experimental.suspendCoroutine

data class Response<T>(val result: T?,
                       val error: Error = ERROR_EMPTY)

open class Error(override val message: String? = null) : Exception()

data class RequestError(val status: Int = 0,
                        override val message: String? = null) : Error(message)

data class ResponseError(val status: Int,
                         override val message: String? = null,
                         val errorBody: String? = null) : Error(message)

val ERROR_EMPTY = RequestError()
val ERROR_INTERNET_NOT_FOUND = RequestError(800, "Отсутсвует интернет соединение")
val ERROR_TIMEOUT = RequestError(900, "Превышено время ожидания")
val ERROR = RequestError(1000, "Произошла ошибка")

suspend fun CoroutineScope.responseJson(host: String,
                                        params: Map<String, String> = hashMapOf()
): Response<JsonObject> = response(host, params, { JsonObject.readFrom(it) })

suspend fun CoroutineScope.response(host: String,
                                    params: Map<String, String> = hashMapOf()
): Response<String> = response(host, params, { it })

suspend fun <T> CoroutineScope.response(host: String,
                                        params: Map<String, String> = hashMapOf(),
                                        postDelay: suspend (String) -> T?
): Response<T> {
    var result: Response<T>? = null
    val job = launch(CommonPool) {
        result = try {
            Response(runRequest(host, params, postDelay))
        } catch (throwable: Throwable) {
            Response(null, throwable.toError())
        }
    }

    delay(1000)
    job.join()
    return result ?: Response<T>(null, ERROR_EMPTY)
}

private fun Throwable.toError() = when (this) {
    is UnknownHostException -> ERROR_INTERNET_NOT_FOUND
    is ResponseError -> this
    is Error -> this
    else -> ERROR
}

private suspend fun <T> runRequest(host: String,
                           params: Map<String, String>,
                           postDelay: suspend (String) -> T?
): T? = postDelay(RETROFIT.get(host, params).await())

private suspend fun Call<String>.await(): String = withTimeoutOrNull(10000) {
    suspendCoroutine<String> { continuation ->
        enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>?, t: Throwable?) {
                t?.let { continuation.resumeWithException(it) } ?: continuation.resumeWithException(ERROR_EMPTY)
            }

            override fun onResponse(call: Call<String>?, response: retrofit2.Response<String>?) {
                response?.body()?.let { continuation.resume(it) }
                        ?: response?.let { continuation.resumeWithException(ResponseError(it.code(), it.message(), it.errorBody().toString())) }
                        ?: continuation.resumeWithException(ERROR_EMPTY)
            }
        })
    }
} ?: throw ERROR_TIMEOUT

private val RETROFIT: IRequest by lazy({
    retrofit2.Retrofit.Builder()
            .baseUrl(IRequest.Urls.BASE)
            .client(OkHttpClient
                    .Builder()
                    .followRedirects(true)
                    .addNetworkInterceptor {
                        val request = it.request().newBuilder().build()
//                        Logger.trace("===> SERVER", "url =", request.url())
//                        Logger.trace("===> SERVER", "method =", request.method())
//                        Logger.trace("===> SERVER", "headers =", request.headers()
//                                .toMultimap()
//                                .map { "\n            ${it.key}:${it.value.toList().first()}" }
//                                .joinToString(prefix = "", postfix = ""))
                        val response = it.proceed(request)
                        Logger.trace("<<<< SERVER", response)
                        response
                    }.build())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(IRequest::class.java)
})

private interface IRequest {
    object Urls {
        val BASE = "https://google.com"
    }

    @GET
    fun get(@Url url: String): Call<String>

    @GET
    fun get(@Url url: String, @QueryMap(encoded = true) params: Map<String, String>): Call<String>

    @POST
    @Headers("Content-Type: application/json")
    fun post(@Url url: String, @Body params: String): Call<String>

    @FormUrlEncoded
    @POST
    fun post(@Url url: String, @FieldMap(encoded = true) params: Map<String, String>): Call<String>
}