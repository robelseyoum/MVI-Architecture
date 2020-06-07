package com.robelseyoum3.mviexample.ui.main.state

import com.robelseyoum3.mviexample.model.BlogPost
import com.robelseyoum3.mviexample.model.User

data class MainViewState(
    var blogPosts: List<BlogPost>? = null,
    var user: User? = null
)