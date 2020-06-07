package com.robelseyoum3.mviexample.mviexample

import androidx.lifecycle.LiveData
import com.robelseyoum3.mviexample.model.BlogPost
import com.robelseyoum3.mviexample.model.User
import com.robelseyoum3.mviexample.util.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("placeholder/user/{userId}")
    fun getBlogPosts(): LiveData<GenericApiResponse<List<BlogPost>>>

    @GET("placeholder/user/{userId}")
    fun getUser(@Path("userId") userId: String): LiveData<GenericApiResponse<User>>

}