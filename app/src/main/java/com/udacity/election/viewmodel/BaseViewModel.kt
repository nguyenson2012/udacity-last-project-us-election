package com.udacity.election.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

abstract class BaseViewModel(app: Application) : AndroidViewModel(app) {
    val showSnackBarStrResource = MutableLiveData<String>()
    val showSnackBarIntResource = MutableLiveData<Int>()
}