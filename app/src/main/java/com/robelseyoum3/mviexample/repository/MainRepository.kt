package com.robelseyoum3.mviexample.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.robelseyoum3.mviexample.mviexample.RetrofitBuilder
import com.robelseyoum3.mviexample.ui.main.state.MainViewState
import com.robelseyoum3.mviexample.util.ApiEmptyResponse
import com.robelseyoum3.mviexample.util.ApiErrorResponse
import com.robelseyoum3.mviexample.util.ApiSuccessResponse

object MainRepository {

    fun getBlogPosts() : LiveData<MainViewState>{
        return Transformations
            .switchMap(RetrofitBuilder.apiService.getBlogPosts()){apiResponse ->
                object : LiveData<MainViewState>(){
                    override fun onActive() {
                        super.onActive()
                        when(apiResponse){

                            is ApiSuccessResponse -> {
                                value = MainViewState(
                                    blogPosts = apiResponse.body
                                )
                            }

                            is ApiErrorResponse -> {
                                value = MainViewState() //Handle error?
                            }

                            is ApiEmptyResponse -> {
                                value = MainViewState() //Handle empty/error
                            }
                        }
                    }
                }
            }
    }

    fun getUser(userId: String) : LiveData<MainViewState>{
        return Transformations
            .switchMap(RetrofitBuilder.apiService.getUser(userId)){apiResponse ->
                object : LiveData<MainViewState>(){
                    override fun onActive() {
                        super.onActive()
                        when(apiResponse){

                            is ApiSuccessResponse -> {
                                value = MainViewState(
                                    user = apiResponse.body
                                )
                            }

                            is ApiErrorResponse -> {
                                value = MainViewState() //Handle error?
                            }

                            is ApiEmptyResponse -> {
                                value = MainViewState() //Handle empty/error
                            }
                        }
                    }
                }
            }
    }
}