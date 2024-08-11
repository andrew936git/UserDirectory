package com.example.userdirectory

import android.widget.ArrayAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel: ViewModel() {
    var list = mutableListOf<String>()
    val userList: MutableLiveData<ArrayAdapter<String>> by lazy { MutableLiveData<ArrayAdapter<String>>() }

}