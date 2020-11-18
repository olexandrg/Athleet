package net.azurewebsites.athleet

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AthleetService
{
    @GET("Users")
    fun getUsers(@Query("UserID") UserID: Int): Call<String>

}

