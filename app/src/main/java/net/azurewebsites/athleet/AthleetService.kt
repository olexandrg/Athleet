package net.azurewebsites.athleet

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AthleetService
{
    @GET("Users/")
    suspend fun getUsers(@Query("UserID") UserID: Int): Response<SearchResultModel>
}

