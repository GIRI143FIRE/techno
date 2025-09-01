# Automated Student Attendance Monitoring and Analytics System

This zip includes:
- `/backend` — Node.js + Express + MongoDB (role-based auth, QR attendance, marks, fees, Excel export)
- `/android` — Android Studio Kotlin app (student/teacher/admin flows with QR scanner)
- `Attendance.postman_collection.json` — quick API tests

## Setup (Backend)
```bash
cd backend
npm install
npm run seed
npm run dev
```
- Admin: `admin@college.edu` / `admin123`
- Manager: `manager@college.edu` / `manager123`

> The `.env` contains your provided MongoDB URI and uses the `college_attendance` database. For security in production, rotate the password and store secrets in safe vaults.

## Setup (Android)
Open `android/` in Android Studio and run. Emulator will call `http://10.0.2.2:4000`.

## Attendance Rules
- If a student scans the QR **on or before 08:30 (Asia/Kolkata)** → `present`
- After 08:30 → `late`
- Not scanned before session close → remains unmarked (you can add an admin job to mark remaining as `absent`).

## What’s Included
- Student login with rollNo + DOB, editable profile.
- Teacher login, can create an attendance session and show QR.
- Student QR scanner marks attendance automatically.
- Admin/Manager can create students; Manager can upsert fees.
- Teachers/Admin/Manager can export students list to Excel.

## Next Steps / Ideas
- Add timetable & geofencing, LMS integration, parent SMS, charts, and a full web dashboard.
- Add "close session" cron to set `absent` for non-scanners.
- Add role-based fine-grained permissions and audit logs.
