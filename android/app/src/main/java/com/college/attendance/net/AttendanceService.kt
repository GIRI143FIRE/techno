package com.college.attendance.net

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AttendanceService {
    @POST("attendance/session")
    fun createSession(@Body body: Map<String, String>): Call<Map<String, String>>

    @GET("attendance/session/{id}/token")
    fun getToken(@Path("id") sessionId: String): Call<Map<String, String>>

    @POST("attendance/scan")
    fun scan(@Body body: Map<String, String>): Call<Map<String, Any>>
}
