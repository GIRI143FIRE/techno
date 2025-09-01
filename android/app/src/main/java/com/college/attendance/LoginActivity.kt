package com.college.attendance

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.college.attendance.net.ApiClient
import com.college.attendance.net.AuthService
import com.college.attendance.net.models.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var roleSpinner: Spinner
    private lateinit var rollNoInput: EditText
    private lateinit var dobInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        roleSpinner = findViewById(R.id.roleSpinner)
        rollNoInput = findViewById(R.id.rollNoInput)
        dobInput = findViewById(R.id.dobInput)
        passwordInput = findViewById(R.id.passwordInput)
        loginBtn = findViewById(R.id.loginBtn)

        roleSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,
            listOf("student","teacher","admin","manager")
        )

        loginBtn.setOnClickListener {
            val role = roleSpinner.selectedItem.toString()
            val roll = rollNoInput.text.toString().trim()
            val dobOrEmail = dobInput.text.toString().trim()
            val pass = passwordInput.text.toString()

            val auth = ApiClient.getClient().create(AuthService::class.java)

            when (role) {
                "student" -> auth.studentLogin(mapOf("rollNo" to roll, "dob" to dobOrEmail))
                "teacher" -> auth.teacherLogin(mapOf("rollNo" to roll, "dob" to dobOrEmail))
                else -> auth.adminLogin(mapOf("email" to dobOrEmail, "password" to pass))
            }.enqueue(object: Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        val body = response.body()!!
                        ApiClient.saveToken(this@LoginActivity, body.token)
                        if (body.user.role == "student") startActivity(Intent(this@LoginActivity, StudentMainActivity::class.java))
                        else if (body.user.role == "teacher") startActivity(Intent(this@LoginActivity, TeacherMainActivity::class.java))
                        else startActivity(Intent(this@LoginActivity, AdminMainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Login failed", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
