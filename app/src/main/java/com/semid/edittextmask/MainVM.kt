package com.semid.edittextmask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainVM:ViewModel() {
    val phoneNumber = MutableLiveData("")
    val text = MutableLiveData("")
}