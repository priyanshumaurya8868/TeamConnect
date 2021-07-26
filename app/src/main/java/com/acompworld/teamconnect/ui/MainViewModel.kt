package com.acompworld.teamconnect.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acompworld.teamconnect.api.model.responses.DirectoryResponse
import com.acompworld.teamconnect.api.model.responses.MyWallResponse
import com.acompworld.teamconnect.repo.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _myWall = MutableLiveData<MyWallResponse>()
      val  myWall : LiveData<MyWallResponse> = _myWall
       init {
           getMyWall()
           getDirectory()
       }

    private val _directory = MutableLiveData<DirectoryResponse>()
    var directory: LiveData<DirectoryResponse> = _directory

    fun getDirectory(projectCode: String= "wrihq", search: String = "") =
        viewModelScope.launch(Dispatchers.IO) {
            val response =  Repo.getDirectory(projectCode, search)
            if (response.isSuccessful && response.body() !=null)
                _directory.postValue(response.body()!!)
            else
                Log.d("omega_directory", "response is null ${response.code()}")
        }

    fun getMyWall(projectCode : String="wrihq" )=viewModelScope.launch{
        val response = Repo.getMyWall(projectCode)
        if (response.isSuccessful && response.body() !=null)
            _myWall.postValue(response.body()!!)
        else
            Log.d("omega_mywall", "response is null ${response.code()}")
    }
}