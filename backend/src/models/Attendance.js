import mongoose from 'mongoose';

const sessionSchema = new mongoose.Schema({
  courseCode: String,
  section: String,
  teacher: { type: mongoose.Schema.Types.ObjectId, ref: 'User', required: true },
  sessionDate: { type: Date, required: true },
  token: { type: String, required: true, unique: true },
  status: { type: String, enum: ['open','closed'], default: 'open' },
}, { timestamps: true });

const recordSchema = new mongoose.Schema({
  session: { type: mongoose.Schema.Types.ObjectId, ref: 'AttendanceSession', required: true },
  student: { type: mongoose.Schema.Types.ObjectId, ref: 'User', required: true },
  timestamp: { type: Date, default: Date.now },
  status: { type: String, enum: ['present','late','absent'], required: true },
}, { timestamps: true });

export const AttendanceSession = mongoose.model('AttendanceSession', sessionSchema);
export const AttendanceRecord = mongoose.model('AttendanceRecord', recordSchema);
