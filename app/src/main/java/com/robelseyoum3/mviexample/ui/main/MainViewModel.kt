package com.robelseyoum3.mviexample.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.robelseyoum3.mviexample.model.BlogPost
import com.robelseyoum3.mviexample.model.User
import com.robelseyoum3.mviexample.ui.main.state.MainStateEvent
import com.robelseyoum3.mviexample.ui.main.state.MainStateEvent.*
import com.robelseyoum3.mviexample.util.AbsentLiveData
import com.robelseyoum3.mviexample.ui.main.state.MainViewState as MainViewState

class MainViewModel: ViewModel() {

    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()
    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()

    //getter method
    val viewState: LiveData<MainViewState>
        get() = _viewState

    val dataState: LiveData<MainViewState> = Transformations
        .switchMap(_stateEvent){ stateEvent ->
            stateEvent?.let {
                handleStateEvent(it)
            }
        }

    private fun handleStateEvent(stateEvent: MainStateEvent): LiveData<MainViewState> {
        return when (stateEvent) {
            is GetBlogPostEvent -> {
                return object: LiveData<MainViewState>(){
                    override fun onActive() {
                        super.onActive()
                        val blogList: ArrayList<BlogPost> = ArrayList()
                        blogList.add(
                            BlogPost(
                                pk = 0,
                                title = "Vancouver PNE 2019",
                                body = "Here is Jess and I at the Vancouver PNE. We ate a lot of food.",
                                image = "https://cdn.open-api.xyz/open-api-static/static-blog-images/image8.jpg"
                            )
                        )
                        blogList.add(
                            BlogPost(
                                pk = 1,
                                title = "Ready for a Walk",
                                body = "Here I am at the park with my dogs Kiba and Maizy. Maizy is the smaller one and Kiba is the larger one.",
                                image = "https://cdn.open-api.xyz/open-api-static/static-blog-images/image2.jpg"
                            )
                        )
                        value = MainViewState(
                            blogPosts = blogList
                        )
                    }
                }
            }

            is GetUserEvent -> {
                return object: LiveData<MainViewState>(){
                    override fun onActive() {
                        super.onActive()
                        val user = User(
                            email = "mitch@tabian.ca",
                            username = "mitch",
                            image = "https://cdn.open-api.xyz/open-api-static/static-random-images/logo_1080_1080.png"
                        )
                        value = MainViewState(
                            user = user
                        )
                    }
                }
            }
            is None -> {
                AbsentLiveData.create()
            }
        }
    }

    fun setBlogListData(blogPost: List<BlogPost>){
        val update = getCurrentViewStateOrNew()
        update.blogPosts= blogPost
        _viewState.value = update
    }

    fun setUser(user: User){
        val update = getCurrentViewStateOrNew()
        update.user = user
        _viewState.value = update
    }


    private fun getCurrentViewStateOrNew(): MainViewState {
        return viewState.value?.let{ it }?: MainViewState()
    }

    fun setStateEvent(event: MainStateEvent){
        _stateEvent.value = event
    }

}