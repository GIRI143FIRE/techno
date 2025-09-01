package com.college.attendance.net

import com.college.attendance.net.models.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/student/login")
    fun studentLogin(@Body body: Map<String, String>): Call<LoginResponse>

    @POST("auth/teacher/login")
    fun teacherLogin(@Body body: Map<String, String>): Call<LoginResponse>

    @POST("auth/admin/login")
    fun adminLogin(@Body body: Map<String, String>): Call<LoginResponse>
}
