package com.acompworld.teamconnect.repo

import com.acompworld.teamconnect.api.service.TeamConnectApi

class TeamConnectRepository(val api : TeamConnectApi){

    suspend fun getMyWall(projectCode: String) = api.getMyWall(projectCode)

    suspend fun getDirectory(projectCode: String, search: String) =
        api.getDirectory(projectCode, search)

    suspend fun  getEmplyoyeeByID(projectCode: String, empID: String)=
        api.getEmployeeById(projectCode,empID)

    suspend fun getContactByID(projectCode: String, empID: String)=
        api.getContactById(projectCode,empID)

   suspend fun  getAboutUs(projectCode: String) = api.getAboutUs(projectCode)
}