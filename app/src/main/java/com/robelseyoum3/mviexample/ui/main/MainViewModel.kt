package com.robelseyoum3.mviexample.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.robelseyoum3.mviexample.ui.main.state.MainViewState

class MainViewModel: ViewModel() {

    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData();

    //getter method
    val viewState: LiveData<MainViewState>
        get() = _viewState


}