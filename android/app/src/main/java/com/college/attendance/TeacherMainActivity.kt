package com.college.attendance

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.college.attendance.net.ApiClient
import com.college.attendance.net.AttendanceService
import net.glxn.qrgen.android.QRCode

class TeacherMainActivity : AppCompatActivity() {
    private lateinit var welcome: TextView
    private lateinit var createBtn: Button
    private lateinit var exportBtn: Button
    private lateinit var qrImage: ImageView
    private var currentSessionId: String? = null
    private var currentToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_main)

        welcome = findViewById(R.id.teacherWelcome)
        createBtn = findViewById(R.id.createSessionBtn)
        exportBtn = findViewById(R.id.exportStudentsBtn)
        qrImage = ImageView(this)
        (findViewById<android.widget.LinearLayout>(android.R.id.content).rootView as? android.widget.LinearLayout)?.addView(qrImage)

        val svc = ApiClient.authed().create(AttendanceService::class.java)

        createBtn.setOnClickListener {
            svc.createSession(mapOf("courseCode" to "CS101", "section" to "A")).enqueue(object: retrofit2.Callback<Map<String, String>> {
                override fun onResponse(call: retrofit2.Call<Map<String, String>>, response: retrofit2.Response<Map<String, String>>) {
                    if (response.isSuccessful) {
                        val body = response.body()!!
                        currentSessionId = body["sessionId"]
                        currentToken = body["token"]
                        welcome.text = "Session: $currentSessionId"
                        val bmp: Bitmap = QRCode.from(currentToken).bitmap()
                        qrImage.setImageBitmap(bmp)
                    } else Toast.makeText(this@TeacherMainActivity, "Failed", Toast.LENGTH_SHORT).show()
                }
                override fun onFailure(call: retrofit2.Call<Map<String, String>>, t: Throwable) { }
            })
        }

        exportBtn.setOnClickListener {
            Toast.makeText(this, "Download from /exports/students.xlsx", Toast.LENGTH_SHORT).show()
        }
    }
}
