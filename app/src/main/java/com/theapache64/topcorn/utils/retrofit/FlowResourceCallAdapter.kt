package com.theapache64.topcorn.utils.retrofit

import com.theapache64.twinkill.network.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.awaitResponse
import java.lang.reflect.Type

/**
 * To convert retrofit response to Flow<Resource<T>>.
 * Inspired from FlowCallAdapterFactory
 */
class FlowResourceCallAdapter<R>(
    private val responseType: Type,
    private val isNeedDeepCheck: Boolean,
    private val isSelfExceptionHandling: Boolean
) : CallAdapter<R, Flow<Resource<R>>> {

    override fun responseType() = responseType

    @ExperimentalCoroutinesApi
    override fun adapt(call: Call<R>): Flow<Resource<R>> = flow {

        // Firing loading resource
        emit(Resource.loading())

        val resp = call.awaitResponse()

        if (resp.isSuccessful) {
            emit(Resource.create(resp, isNeedDeepCheck))
        } else {
            emit(Resource.create<R>(Throwable(resp.message())))
        }

    }.catch { error ->
        if (isSelfExceptionHandling) {
            emit(Resource.create<R>(error))
        } else {
            throw error
        }
    }
}