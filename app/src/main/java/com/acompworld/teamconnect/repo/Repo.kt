package com.acompworld.teamconnect.repo

import com.acompworld.teamconnect.api.service.MyClient

object Repo {

    val api = MyClient.api

    suspend fun getMyWall(projectCode: String) = api.getMyWall(projectCode)

    suspend fun getDirectory(projectCode: String, search: String) =
        api.getDirectory(projectCode, search)

    suspend fun  getEmplyoyeeByID(projectCode: String, empID: String)=
        api.getEmployeeById(projectCode,empID)

    suspend fun getContactByID(projectCode: String, empID: String)=
        api.getContactById(projectCode,empID)
}