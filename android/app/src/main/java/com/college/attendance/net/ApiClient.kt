package com.college.attendance.net

import android.content.Context
import android.preference.PreferenceManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private var token: String? = null

    fun getClient(): Retrofit {
        val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:4000/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(logging).build())
            .build()
    }

    fun authed(): Retrofit {
        val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        val auth = Interceptor { chain ->
            val t = token
            val req = if (t != null) chain.request().newBuilder().addHeader("Authorization", "Bearer $t").build() else chain.request()
            chain.proceed(req)
        }
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:4000/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(auth).addInterceptor(logging).build())
            .build()
    }

    fun saveToken(ctx: Context, t: String) {
        token = t
        val prefs = PreferenceManager.getDefaultSharedPreferences(ctx)
        prefs.edit().putString("token", t).apply()
    }
}
