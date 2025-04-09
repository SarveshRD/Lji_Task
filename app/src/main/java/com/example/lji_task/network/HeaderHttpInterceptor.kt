package com.example.lji_task.network

import com.example.lji_task.constants.AppConstants
import com.wss.eat_space_iz.utils.sessionManagers.UserSessionManager
import okhttp3.Interceptor
import okhttp3.Response

class HeaderHttpInterceptor(private var userSessionManager : UserSessionManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request().run {
            var curBaseUrl = this.url
            newBuilder().header("Accept", "application/json").apply {
                if (!curBaseUrl.toString().contains(AppConstants.UserEndUrl.BASE_URL))
                    userSessionManager.userToken?.let {
                    header("api-key", "$it")
                }
            }.method(method, body).build()

        })
    }
}