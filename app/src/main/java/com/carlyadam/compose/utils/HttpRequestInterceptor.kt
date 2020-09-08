package com.carlyadam.compose.utils

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.carlyadam.compose.R
import okhttp3.Interceptor
import okhttp3.Response

class HttpRequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val request = originalRequest.newBuilder().url(originalRequest.url).build()
        Log.i("REQUEST",request.toString())
        return chain.proceed(request)
    }
}