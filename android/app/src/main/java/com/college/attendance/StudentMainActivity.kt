package com.college.attendance

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.college.attendance.net.ApiClient
import com.college.attendance.net.StudentService
import com.college.attendance.net.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentMainActivity : AppCompatActivity() {
    private lateinit var welcome: TextView
    private lateinit var nameInput: EditText
    private lateinit var deptInput: EditText
    private lateinit var phoneInput: EditText
    private lateinit var saveBtn: Button
    private lateinit var scanBtn: Button
    private lateinit var marksBtn: Button
    private lateinit var feesBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_main)

        welcome = findViewById(R.id.welcomeText)
        nameInput = findViewById(R.id.nameInput)
        deptInput = findViewById(R.id.deptInput)
        phoneInput = findViewById(R.id.phoneInput)
        saveBtn = findViewById(R.id.saveProfileBtn)
        scanBtn = findViewById(R.id.scanQRBtn)
        marksBtn = findViewById(R.id.viewMarksBtn)
        feesBtn = findViewById(R.id.viewFeesBtn)

        val svc = ApiClient.authed().create(StudentService::class.java)

        svc.me().enqueue(object: Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val u = response.body()!!
                    welcome.text = "Welcome, ${u.name} (${u.rollNo})"
                    nameInput.setText(u.name)
                    deptInput.setText(u.department ?: "")
                    phoneInput.setText(u.phone ?: "")
                }
            }
            override fun onFailure(call: Call<User>, t: Throwable) { }
        })

        saveBtn.setOnClickListener {
            val body = mapOf("name" to nameInput.text.toString(), "department" to deptInput.text.toString(), "phone" to phoneInput.text.toString())
            svc.updateMe(body).enqueue(object: Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    Toast.makeText(this@StudentMainActivity, if (response.isSuccessful) "Saved" else "Failed", Toast.LENGTH_SHORT).show()
                }
                override fun onFailure(call: Call<User>, t: Throwable) { }
            })
        }

        scanBtn.setOnClickListener { startActivity(Intent(this, ScanQRActivity::class.java)) }
        marksBtn.setOnClickListener { Toast.makeText(this, "Use GET /marks/me API (add UI later)", Toast.LENGTH_SHORT).show() }
        feesBtn.setOnClickListener { Toast.makeText(this, "Use GET /fees/me API (add UI later)", Toast.LENGTH_SHORT).show() }
    }
}
