import express from 'express';
import mongoose from 'mongoose';
import cors from 'cors';
import morgan from 'morgan';
import dotenv from 'dotenv';
import { router as authRoutes } from './routes/auth.js';
import { router as studentRoutes } from './routes/students.js';
import { router as teacherRoutes } from './routes/teachers.js';
import { router as adminRoutes } from './routes/admin.js';
import { router as attendanceRoutes } from './routes/attendance.js';
import { router as marksRoutes } from './routes/marks.js';
import { router as feesRoutes } from './routes/fees.js';
import { router as exportRoutes } from './routes/exports.js';

dotenv.config();

const app = express();
app.use(cors());
app.use(express.json());
app.use(morgan('dev'));

app.get('/', (req, res) => res.send({ ok: true, message: 'Attendance Analytics API' }));

app.use('/auth', authRoutes);
app.use('/students', studentRoutes);
app.use('/teachers', teacherRoutes);
app.use('/admin', adminRoutes);
app.use('/attendance', attendanceRoutes);
app.use('/marks', marksRoutes);
app.use('/fees', feesRoutes);
app.use('/exports', exportRoutes);

const PORT = process.env.PORT || 4000;
const MONGO_URI = process.env.MONGO_URI;

mongoose.connect(MONGO_URI, { dbName: 'college_attendance' })
  .then(() => {
    console.log('MongoDB connected');
    app.listen(PORT, () => console.log(`Server running on http://localhost:${PORT}`));
  })
  .catch(err => {
    console.error('Mongo connection error:', err.message);
    process.exit(1);
  });
