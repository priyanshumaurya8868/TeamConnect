package com.acompworld.teamconnect.ui

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acompworld.teamconnect.R
import com.acompworld.teamconnect.api.model.responses.AboutRespone
import com.acompworld.teamconnect.api.model.responses.DirectoryResponse
import com.acompworld.teamconnect.api.model.responses.MyWallResponse
import com.acompworld.teamconnect.repo.TeamConnectRepository
import com.acompworld.teamconnect.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val cm: ConnectivityManager,
    val repo: TeamConnectRepository
) : ViewModel() {

    private var initiallyCalledAboutUS= false
    var projectCode: String? = null
    var hasIntentConnection: Boolean = false
    private var initiallyCalledMyWall = false
    private var initiallyCalledDirectory = false
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            hasIntentConnection = true
            if (_myWall.value?.data == null && initiallyCalledMyWall) getMyWall()
            if (_directory.value?.data == null && initiallyCalledDirectory) getDirectory()
        }override fun onLost(network: Network) {
            super.onLost(network)
            hasIntentConnection = false
        }
    }

    var HOME_TOGGLE_BTN_ID :Int = R.id.tag_all
    var ABOUT_TOGGLE_BTN_ID : Int = R.id.tag_vision

    val readMore = MutableLiveData(true)
    private val builder = NetworkRequest.Builder()

    private val _myWall = MutableLiveData<Resource<MyWallResponse?>>()
    val myWall: LiveData<Resource<MyWallResponse?>> = _myWall

    private val _directory = MutableLiveData<Resource<DirectoryResponse?>>()
    var directory: LiveData<Resource<DirectoryResponse?>> = _directory

    private val _about = MutableLiveData<Resource<AboutRespone?>>()
    var about : LiveData<Resource<AboutRespone?>> = _about

    init {
        cm.registerNetworkCallback(builder.build(), networkCallback)
        getMyWall()
        getDirectory()
        getAboutUs()
    }

    private fun getAboutUs(projectCode: String = "wrihq") = viewModelScope.launch{
        initiallyCalledAboutUS = true
        this@MainViewModel.projectCode = projectCode
        _about.postValue(Resource.Loading())
        delay(100)
        try {
            if (hasIntentConnection){
                val respone = repo.getAboutUs(projectCode)
                handleAboutResource(respone)
            } else _about.postValue(
                Resource.Error(
                    data = _about.value?.data,
                    msg = "No Internet Connection...!"
                )
            )
        }catch (e: Exception) {
            _about.postValue(
                Resource.Error(
                    data = _about.value?.data,
                    msg = e.message ?: "Something went Wrong"
                )
            )
        }
    }


    fun getMyWall(projectCode: String = "wrihq") = viewModelScope.launch {
        initiallyCalledMyWall = true
        this@MainViewModel.projectCode = projectCode
        _myWall.postValue(Resource.Loading())
        delay(100)
        try {
            if (hasIntentConnection) {
                val response = repo.getMyWall(projectCode)
                handleMyWallResponse(response)
            } else _myWall.postValue(
                Resource.Error(
                    data = _myWall.value?.data,
                    msg = "No Internet Connection...!"
                )
            )
        } catch (e: Exception) {
            _myWall.postValue(
                Resource.Error(
                    data = _myWall.value?.data,
                    msg = e.message ?: "Something went Wrong"
                )
            )
        }
    }



    fun getDirectory(projectCode: String = "wrihq", search: String = "") = viewModelScope.launch {
        initiallyCalledDirectory = true
        this@MainViewModel.projectCode = projectCode
        _directory.postValue(Resource.Loading())
        delay(100)
        try {
            if (hasIntentConnection) {
                val response = repo.getDirectory(projectCode, search)
                handleDirectoryResponse(response)
            } else _directory.postValue(
                Resource.Error(
                    data = _directory.value?.data,
                    msg = "No Internet Connection...!"
                )
            )
        } catch (e: Exception) {
            _directory.postValue(
                Resource.Error(
                    data = _directory.value?.data,
                    msg = e.message ?: "Something went Wrong"
                )
            )
        }
    }

    private fun handleDirectoryResponse(response: Response<DirectoryResponse>) {
        if (response.isSuccessful) {
            _directory.postValue(Resource.Success(response.body()!!))
        } else _directory.postValue(
            Resource.Error(
                data = _directory.value?.data,
                msg = response.message() ?: "Error ${response.code()} found...!"
            )
        )
    }


    private fun handleAboutResource(response: Response<AboutRespone>) {
       if (response.isSuccessful)
           _about.postValue(Resource.Success(response.body()))
        else _about.postValue(
           Resource.Error(
               data = _about.value?.data,
               msg = response.message()?:  "Error ${response.code()} found...!"
           )
        )
    }

    private fun handleMyWallResponse(response: Response<MyWallResponse>) {
        if (response.isSuccessful)
            _myWall.postValue(Resource.Success(response.body()!!))
        else _myWall.postValue(
            Resource.Error(
                data = _myWall.value?.data,
                msg = response.message() ?: "Error ${response.code()} found...!"
            )
        )
    }
}