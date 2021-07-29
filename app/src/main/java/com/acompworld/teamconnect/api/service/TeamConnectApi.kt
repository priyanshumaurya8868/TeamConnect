package com.acompworld.teamconnect.api.service

import com.acompworld.teamconnect.api.model.responses.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TeamConnectApi {

    @GET("mywall")
    suspend fun getMyWall(
        @Query("projcode") projcode:String
    ):Response<MyWallResponse>

    @GET("directory")
    suspend fun getDirectory(
        @Query("projcode") projectCode : String,
        @Query("search")  search : String= ""
    ): Response<DirectoryResponse>


    @GET("empprofile")
    suspend fun getEmployeeById(
        @Query("projcode") projectCode : String,
        @Query("empcode")  empCode : String
    ):Response<EmployeeResponse>


    @GET("contactinfo")
    suspend fun getContactById(
        @Query("projcode") projectCode : String,
        @Query("id") id : String
    ):Response<ContactResponse>

    @GET("about")
    suspend fun getAboutUs(
        @Query("projcode") projcode:String
    ):Response<AboutRespone>

    suspend fun getDepartments(

    )


}