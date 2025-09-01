# Attendance & Analytics Backend

## Quick Start
1. Create `.env` (already included here):
```
PORT=4000
JWT_SECRET=supersecret_jwt_key_change_me
MONGO_URI=...
TZ=Asia/Kolkata
```
2. Install & run:
```bash
npm install
npm run seed   # creates admin and manager accounts
npm run dev
```
- Admin: `admin@college.edu` / `admin123`
- Manager: `manager@college.edu` / `manager123`

## Key Endpoints
- `POST /auth/student/login { rollNo, dob }`
- `POST /auth/teacher/login { rollNo, dob }`
- `POST /auth/admin/login { email, password }`

- `GET /students/me` (any role) | `PATCH /students/me` (student/teacher)
- `GET /students` (teacher/admin/manager)
- `POST /admin/students` | `POST /admin/teachers` (admin/manager)
- Attendance:
  - `POST /attendance/session` (teacher) -> {sessionId, token}
  - `GET /attendance/session/:id/token` (teacher) -> {token}  (teacher shows as QR on their app)
  - `POST /attendance/scan` (student) -> marks present/late by 08:30 IST logic
  - `POST /attendance/session/:id/close` (teacher/admin)
  - `GET /attendance/session/:id/report` (teacher/admin)
- Marks:
  - `POST /marks` (teacher)
  - `GET /marks/me` (student)
  - `GET /marks/student/:id` (teacher/admin)
- Fees:
  - `POST /fees` (manager/admin)
  - `GET /fees/me` (student)
- Export:
  - `GET /exports/students.xlsx` (teacher/admin/manager)

## Notes
- Students/Teachers login via `rollNo + dob (YYYY-MM-DD)`.
- Use `Asia/Kolkata` timezone for attendance cutoff at 08:30.
- Extend models for timetable, LMS integration, etc.
