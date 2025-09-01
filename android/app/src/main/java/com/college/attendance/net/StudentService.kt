package com.college.attendance.net

import com.college.attendance.net.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface StudentService {
    @GET("students/me")
    fun me(): Call<User>

    @PATCH("students/me")
    fun updateMe(@Body body: Map<String, Any>): Call<User>
}
