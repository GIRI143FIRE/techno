package com.college.attendance.net

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AdminService {
    @POST("admin/students")
    fun createStudent(@Body body: Map<String, Any>): Call<Map<String, Any>>
}
