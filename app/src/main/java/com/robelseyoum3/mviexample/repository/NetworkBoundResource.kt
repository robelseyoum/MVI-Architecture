package com.robelseyoum3.mviexample.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.robelseyoum3.mviexample.util.*
import com.robelseyoum3.mviexample.util.Constants.Companion.TESTING_NETWORK_DELAY
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class NetworkBoundResource<ResponseObject, ViewStateType> {

   protected val result = MediatorLiveData<DataState<ViewStateType>>()

    init {
        result.value = DataState.loading(true) //here showing progressbar

        GlobalScope.launch(IO){

            delay(TESTING_NETWORK_DELAY)
                //switching to main thread
            withContext(Main){
                val apiResponse = createCall()

                result.addSource(apiResponse){ response ->
                    handleNetworkCall(response)
                }
            }
        }
    }

    private fun handleNetworkCall(response: GenericApiResponse<ResponseObject>){
        when(response){

            is ApiSuccessResponse -> {
                handleApiSuccessResponse(response)
            }

            is ApiErrorResponse -> {
                //Handle error?
                println("DEBUG: NetworkBoundResource: ${response.errorMessage}")
                onReturnError(response.errorMessage)
            }

            is ApiEmptyResponse -> {
                //Handle empty/error
                println("DEBUG: NetworkBoundResource: HTTP 204. Returned NOTHING!")
                onReturnError("HTTP 204. Returned NOTHING!")
            }
        }
    }

    private fun onReturnError(message: String){
        result.value = DataState.error(message)
    }

    //ResponseObject is either user or blog
    abstract fun createCall(): LiveData<GenericApiResponse<ResponseObject>>

    abstract fun handleApiSuccessResponse(response: ApiSuccessResponse<ResponseObject>)

    fun asLiveData() = result as LiveData<DataState<ViewStateType>> //change MediatorLiveData to LiveData
}