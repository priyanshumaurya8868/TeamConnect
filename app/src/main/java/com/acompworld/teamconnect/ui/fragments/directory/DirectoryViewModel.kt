package com.acompworld.teamconnect.ui.fragments.directory

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acompworld.teamconnect.api.model.responses.ContactResponse
import com.acompworld.teamconnect.api.model.responses.EmployeeResponse
import com.acompworld.teamconnect.repo.Repo
import kotlinx.coroutines.launch

class DirectoryViewModel : ViewModel() {

    private val _employeeResponse = MutableLiveData<EmployeeResponse>()
    val employee: LiveData<EmployeeResponse> = _employeeResponse

    private val _contactResponse = MutableLiveData<ContactResponse>()
    val contact: LiveData<ContactResponse> = _contactResponse

    fun getEmployeeByID(projectCode: String, empID: String) = viewModelScope.launch {
        val response = Repo.getEmplyoyeeByID(projectCode, empID)

        if (response.isSuccessful && response.body() != null)
            _employeeResponse.postValue(response.body()!!)
        else
            Log.d("omega_empByID", "response is null ${response.code()}")
    }

    fun getContactByID(projectCode: String, contactID: String) = viewModelScope.launch {
        val response = Repo.getContactByID(projectCode, contactID)

        if (response.isSuccessful && response.body() != null)
            _contactResponse.postValue(response.body()!!)
        else
            Log.d("omega_empByID", "response is null ${response.code()}")
    }


}

