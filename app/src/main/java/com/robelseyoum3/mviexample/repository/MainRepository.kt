package com.robelseyoum3.mviexample.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.robelseyoum3.mviexample.model.BlogPost
import com.robelseyoum3.mviexample.model.User
import com.robelseyoum3.mviexample.mviexample.RetrofitBuilder
import com.robelseyoum3.mviexample.ui.main.state.MainViewState
import com.robelseyoum3.mviexample.util.*

object MainRepository {

    fun getBlogPosts() : LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<List<BlogPost>, MainViewState>(){

            override fun createCall(): LiveData<GenericApiResponse<List<BlogPost>>> {
                return RetrofitBuilder.apiService.getBlogPosts()
            }

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<BlogPost>>) {
                result.value = DataState.data(
                    data = MainViewState(
                        blogPosts = response.body,
                        user = null
                    )
                )
            }

        }.asLiveData()
    }

    fun getUser(userId: String) : LiveData<DataState<MainViewState>>{

        return object : NetworkBoundResource<User, MainViewState>(){

            override fun createCall(): LiveData<GenericApiResponse<User>> {
                return RetrofitBuilder.apiService.getUser(userId)
            }

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<User>) {
                result.value = DataState.data(
                    data =  MainViewState(
                        blogPosts = null,
                        user = response.body
                    )
                )
            }
        }.asLiveData()

    }
}