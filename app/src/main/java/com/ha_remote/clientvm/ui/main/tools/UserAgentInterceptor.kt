package com.ha_remote.clientvm.ui.main.tools

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


/* This interceptor adds a custom User-Agent. */
class UserAgentInterceptor(private val userAgent: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val requestWithUserAgent: Request = originalRequest.newBuilder()
            .header("User-Agent", userAgent)
            .build()
        return chain.proceed(requestWithUserAgent)
    }
}