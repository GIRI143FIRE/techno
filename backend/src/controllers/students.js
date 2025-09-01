import { User } from '../models/User.js';

export async function me(req, res) {
  const me = await User.findById(req.user.id);
  res.json(me);
}

export async function updateMe(req, res) {
  const allowed = ['name','department','phone','avatarUrl','address','year','guardian','email'];
  const updates = {};
  for (const k of allowed) if (k in req.body) updates[k] = req.body[k];
  const me = await User.findByIdAndUpdate(req.user.id, updates, { new: true });
  res.json(me);
}

export async function listStudents(req, res) {
  const students = await User.find({ role: 'student' }).select('-passwordHash -dob');
  res.json(students);
}
