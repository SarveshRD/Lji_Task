package com.example.lji_task.network



import com.example.lji_task.constants.AppConstants
import com.example.lji_task.data.LjiApiResponse
import retrofit2.http.*


interface ApiService {


    @GET(AppConstants.UserEndUrl.LJI_URL)
    suspend fun getData(): LjiApiResponse



}