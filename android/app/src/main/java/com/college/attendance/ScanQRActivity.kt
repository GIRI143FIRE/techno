package com.college.attendance

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.college.attendance.net.ApiClient
import com.college.attendance.net.AttendanceService
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScanQRActivity : AppCompatActivity() {
    private lateinit var statusText: TextView
    private lateinit var startBtn: Button

    private val launcher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents != null) {
            val token = result.contents
            val svc = ApiClient.authed().create(AttendanceService::class.java)
            svc.scan(mapOf("token" to token)).enqueue(object: Callback<Map<String, Any>> {
                override fun onResponse(call: Call<Map<String, Any>>, response: Response<Map<String, Any>>) {
                    if (response.isSuccessful) statusText.text = "Attendance marked: " + response.body()!!["status"]
                    else statusText.text = "Failed to mark attendance"
                }
                override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) { statusText.text = t.message }
            })
        } else {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_qr)

        statusText = findViewById(R.id.scanStatus)
        startBtn = findViewById(R.id.startScanBtn)

        startBtn.setOnClickListener {
            val options = ScanOptions().setDesiredBarcodeFormats(ScanOptions.QR_CODE).setPrompt("Scan session QR")
            launcher.launch(options)
        }
    }
}
