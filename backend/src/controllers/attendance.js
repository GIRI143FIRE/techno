import { AttendanceSession, AttendanceRecord } from '../models/Attendance.js';
import { DateTime } from 'luxon';
import { v4 as uuidv4 } from 'uuid';

// Helper: compute status based on 08:30 IST
function computeStatus(tsISO) {
  const ts = DateTime.fromISO(tsISO, { zone: 'Asia/Kolkata' });
  const eightThirty = ts.set({ hour: 8, minute: 30, second: 0, millisecond: 0 });
  if (ts <= eightThirty) return 'present';
  return 'late';
}

export async function createSession(req, res) {
  const { courseCode, section, sessionDate } = req.body;
  const token = uuidv4();
  const date = sessionDate ? new Date(sessionDate) : new Date();
  const session = await AttendanceSession.create({
    courseCode, section, teacher: req.user.id, sessionDate: date, token, status: 'open'
  });
  res.status(201).json({ sessionId: session._id, token });
}

export async function closeSession(req, res) {
  const { id } = req.params;
  const session = await AttendanceSession.findByIdAndUpdate(id, { status: 'closed' }, { new: true });
  res.json(session);
}

export async function qrToken(req, res) {
  const { id } = req.params;
  const session = await AttendanceSession.findById(id);
  if (!session) return res.status(404).json({ error: 'Session not found' });
  res.json({ token: session.token });
}

export async function scan(req, res) {
  const { token } = req.body;
  const session = await AttendanceSession.findOne({ token, status: 'open' });
  if (!session) return res.status(400).json({ error: 'Invalid or closed session' });
  const ts = DateTime.now().setZone('Asia/Kolkata').toISO();
  const status = computeStatus(ts);
  const record = await AttendanceRecord.findOneAndUpdate(
    { session: session._id, student: req.user.id },
    { timestamp: new Date(ts), status },
    { upsert: true, new: true }
  );
  res.json(record);
}

export async function sessionReport(req, res) {
  const { id } = req.params;
  const records = await AttendanceRecord.find({ session: id }).populate('student','name rollNo department year');
  res.json(records);
}
