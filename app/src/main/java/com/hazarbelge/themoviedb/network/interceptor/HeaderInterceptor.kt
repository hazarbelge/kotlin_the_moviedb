package com.hazarbelge.themoviedb.network.interceptor

import android.os.Build

import java.util.*

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    private val defaultLocale = Locale.getDefault()

    private var language: String? = null
    private var region: String? = null
    private var osVersion: String? = null
    private var deviceType: String? = null

    override fun intercept(chain: Interceptor.Chain): Response {

        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .header("Device-Language", deviceLanguage())
            .header("Device-Region", deviceRegion())
            .header("OS-Version", osVersion())
            .header("Device-Type", deviceType())
            .header("App-Platform", "Android")
            .method(original.method, original.body)
        /*if (sessionID != null) {
            requestBuilder.header("Authorization", "Bearer $sessionID")
        }*/

        val request = requestBuilder.build()
        return chain.proceed(request)
    }

    private fun deviceLanguage(): String {
        language = Locale.getDefault().getLanguage()
        if (language == null) {
            language = "Unknown"
        }

        return language!!
    }

    private fun deviceRegion(): String {
        if (region == null) {
            region = defaultLocale.country
            if (region == null) {
                region = "Unknown"
            }
        }

        return region!!
    }

    private fun osVersion(): String {
        if (osVersion == null) {
            osVersion = Build.VERSION.SDK_INT.toString() + ""
        }
        return osVersion!!
    }

    private fun deviceType(): String {
        if (deviceType == null) {
            deviceType = Build.DEVICE
            if (deviceType == null) {
                deviceType = "Unknown"
            }
        }

        return deviceType!!
    }
}