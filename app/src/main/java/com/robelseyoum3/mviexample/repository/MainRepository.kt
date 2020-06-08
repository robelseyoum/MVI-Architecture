package com.robelseyoum3.mviexample.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.robelseyoum3.mviexample.mviexample.RetrofitBuilder
import com.robelseyoum3.mviexample.ui.main.state.MainViewState
import com.robelseyoum3.mviexample.util.ApiEmptyResponse
import com.robelseyoum3.mviexample.util.ApiErrorResponse
import com.robelseyoum3.mviexample.util.ApiSuccessResponse
import com.robelseyoum3.mviexample.util.DataState

object MainRepository {

    fun getBlogPosts() : LiveData<DataState<MainViewState>>{
        return Transformations
            .switchMap(RetrofitBuilder.apiService.getBlogPosts()){apiResponse ->
                object : LiveData<DataState<MainViewState>>(){
                    override fun onActive() {
                        super.onActive()
                        when(apiResponse){

                            is ApiSuccessResponse -> {
                                value = DataState.data(
                                    data = MainViewState(
                                        blogPosts = apiResponse.body
                                    )
                                )
                            }

                            is ApiErrorResponse -> {
                                //Handle error?
                                value = DataState.error(
                                    message = apiResponse.errorMessage
                                )
                            }

                            is ApiEmptyResponse -> {
                                //Handle empty/error
                                value = DataState.error(
                                    message = "HTTP 204. Returned NOTHING!"
                                )
                            }
                        }
                    }
                }
            }
    }

    fun getUser(userId: String) : LiveData<DataState<MainViewState>>{
        return Transformations
            .switchMap(RetrofitBuilder.apiService.getUser(userId)){ apiResponse ->
                object : LiveData<DataState<MainViewState>>(){
                    override fun onActive() {
                        super.onActive()
                        when(apiResponse){

                            is ApiSuccessResponse -> {
                                value = DataState.data(
                                    data = MainViewState(
                                        user = apiResponse.body
                                    )
                                )
                            }

                            is ApiErrorResponse -> {
                                //Handle empty/error
                                value = DataState.error(
                                    message = "HTTP 204. Returned NOTHING!"
                                )
                            }

                            is ApiEmptyResponse -> {
                                //Handle empty/error
                                value = DataState.error(
                                    message = "HTTP 204. Returned NOTHING!"
                                )
                            }
                        }
                    }
                }
            }
    }
}