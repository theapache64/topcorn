package com.theapache64.topcorn.utils

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.theapache64.twinkill.logger.debug
import com.theapache64.twinkill.logger.info
import com.theapache64.twinkill.logger.mistake
import com.theapache64.twinkill.network.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

/**
 * A super cool utility class to provide controlled data cache-ing
 */
abstract class NetworkBoundResource<DB, REMOTE> {

    @MainThread
    abstract fun fetchFromLocal(): Flow<DB>

    @MainThread
    abstract fun fetchFromRemote(): Flow<Resource<REMOTE>>

    @WorkerThread
    abstract fun saveRemoteData(data: REMOTE)

    @MainThread
    abstract fun shouldFetchFromRemote(data: DB): Boolean

    @ExperimentalCoroutinesApi
    fun asFlow() = flow<Resource<DB>> {

        debug("----------------------")
        debug("Starting...")


        val localData = fetchFromLocal().first()

        // checking if local data is staled
        if (shouldFetchFromRemote(localData)) {

            debug("Fetching from remote")
            // need remote data
            fetchFromRemote()
                .collect { response ->
                    when (response.status) {

                        Resource.Status.LOADING -> {
                            debug("Remote is loading")
                            emit(Resource.loading())
                        }

                        Resource.Status.SUCCESS -> {
                            info("Remote got data")
                            val data = response.data!!
                            saveRemoteData(data)

                            // start watching it
                            emitLocalDbData()
                        }


                        Resource.Status.ERROR -> {
                            mistake("Remote met with an error")
                            emit(Resource.error(response.message!!))
                        }
                    }
                }

        } else {
            info("Fetching from local")
            // valid cache, no need to fetch from remote.
            emitLocalDbData()
        }
    }

    @ExperimentalCoroutinesApi
    private suspend fun FlowCollector<Resource<DB>>.emitLocalDbData() {
        info("Sending local data to UI")
        // sending loading status
        emit(Resource.loading())

        emitAll(fetchFromLocal().map { dbData ->
            info("Sending local...")
            Resource.success(dbData)
        })
    }
}