package com.robelseyoum3.mviexample.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.robelseyoum3.mviexample.ui.main.state.MainStateEvent
import com.robelseyoum3.mviexample.ui.main.state.MainViewState
import com.robelseyoum3.mviexample.util.AbsentLiveData

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
        return when(stateEvent) {

            is MainStateEvent.GetBlogPostEvent -> {
                AbsentLiveData.create()
            }

            is MainStateEvent.GetUserEvent -> {
                AbsentLiveData.create()
            }

            is MainStateEvent.None -> {
                AbsentLiveData.create()
            }
        }
    }

}