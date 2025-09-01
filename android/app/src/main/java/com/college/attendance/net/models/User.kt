package com.college.attendance.net.models

data class User(
    val _id: String,
    val role: String,
    val rollNo: String?,
    val dob: String?,
    val email: String?,
    val name: String,
    val department: String?,
    val phone: String?
)
