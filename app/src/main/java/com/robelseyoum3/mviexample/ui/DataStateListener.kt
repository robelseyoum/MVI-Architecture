package com.robelseyoum3.mviexample.ui

import com.robelseyoum3.mviexample.util.DataState

interface DataStateListener {
    fun onDataStateChange(dataState: DataState<*>?) //type invariant or any type
}