package com.robelseyoum3.mviexample.ui.main.state

sealed class MainStateEvent {

    object GetBlogPostEvent: MainStateEvent()

    class GetUserEvent(val userID: String) : MainStateEvent()

    object None: MainStateEvent()

}