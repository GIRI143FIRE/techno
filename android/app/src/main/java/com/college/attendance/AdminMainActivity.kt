package com.college.attendance

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.college.attendance.net.AdminService
import com.college.attendance.net.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main)

        val roll = findViewById<EditText>(R.id.studentRoll)
        val dob = findViewById<EditText>(R.id.studentDob)
        val name = findViewById<EditText>(R.id.studentName)
        val dept = findViewById<EditText>(R.id.studentDept)
        val createBtn = findViewById<Button>(R.id.createStudentBtn)

        val feesJson = findViewById<EditText>(R.id.feesJson)
        val saveFeesBtn = findViewById<Button>(R.id.saveFeesBtn)

        val admin = ApiClient.authed().create(AdminService::class.java)

        createBtn.setOnClickListener {
            val body = mapOf("rollNo" to roll.text.toString(), "dob" to dob.text.toString(),
                "name" to name.text.toString(), "department" to dept.text.toString(), "year" to 1, "phone" to "")
            admin.createStudent(body).enqueue(simpleToastCallback("Created student"))
        }

        saveFeesBtn.setOnClickListener {
            try {
                val items = feesJson.text.toString()
                val studentRoll = roll.text.toString()
                // In a real app you'd lookup studentId; for demo assume admin created and has ID via separate UI.
                Toast.makeText(this, "Use POST /fees with studentId + items JSON", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun <T> simpleToastCallback(successMsg: String) = object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            Toast.makeText(this@AdminMainActivity, if (response.isSuccessful) successMsg else "Failed", Toast.LENGTH_SHORT).show()
        }
        override fun onFailure(call: Call<T>, t: Throwable) {
            Toast.makeText(this@AdminMainActivity, t.message, Toast.LENGTH_SHORT).show()
        }
    }
}
