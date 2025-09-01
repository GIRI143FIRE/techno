import bcrypt from 'bcryptjs';
import jwt from 'jsonwebtoken';
import { User } from '../models/User.js';

function sign(user) {
  return jwt.sign({ id: user._id, role: user.role, name: user.name }, process.env.JWT_SECRET, { expiresIn: '7d' });
}

export async function studentLogin(req, res) {
  const { rollNo, dob } = req.body;
  const user = await User.findOne({ role: 'student', rollNo, dob });
  if (!user) return res.status(401).json({ error: 'Invalid credentials' });
  return res.json({ token: sign(user), user });
}

export async function teacherLogin(req, res) {
  const { rollNo, dob } = req.body;
  const user = await User.findOne({ role: 'teacher', rollNo, dob });
  if (!user) return res.status(401).json({ error: 'Invalid credentials' });
  return res.json({ token: sign(user), user });
}

export async function adminLogin(req, res) {
  const { email, password } = req.body;
  const user = await User.findOne({ role: { $in: ['admin','manager'] }, email });
  if (!user) return res.status(401).json({ error: 'Invalid credentials' });
  const ok = await bcrypt.compare(password, user.passwordHash || '');
  if (!ok) return res.status(401).json({ error: 'Invalid credentials' });
  return res.json({ token: sign(user), user });
}
