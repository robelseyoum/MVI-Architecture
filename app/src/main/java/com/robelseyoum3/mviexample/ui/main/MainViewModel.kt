package com.robelseyoum3.mviexample.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.robelseyoum3.mviexample.model.BlogPost
import com.robelseyoum3.mviexample.model.User
import com.robelseyoum3.mviexample.repository.MainRepository
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
                return MainRepository.getBlogPosts()
            }

            is GetUserEvent -> {
                return MainRepository.getUser(stateEvent.userID)
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