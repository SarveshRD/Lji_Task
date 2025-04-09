package com.example.lji_task.ui.repository


import android.util.Log
import com.example.lji_task.base.ApiResult
import com.example.lji_task.base.BaseRepo
import com.example.lji_task.data.LjiApiResponse
import com.example.lji_task.network.ApiService
import javax.inject.Inject

class HomeTabRepository
@Inject constructor(private val apiCall: ApiService): BaseRepo(){

    suspend fun getData(
        onError: ((ApiResult<Any>) -> Unit)?,
    ): LjiApiResponse? {
        Log.d("HomeTabRepository**********", "Calling getData()")
        return with(apiCall(executable = {
            apiCall.getData()
        })) {
            Log.d("HomeTabRepository**********", "API result: $this")
            if (data == null)
                onError?.invoke(ApiResult(null, resultType, error, resCode = resCode))
            data
        }
    }



}