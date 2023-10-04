package com.semid.edittextmask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainVM:ViewModel() {
    val phoneNumber = MutableLiveData("78687687687")
    val text = MutableLiveData("")
}