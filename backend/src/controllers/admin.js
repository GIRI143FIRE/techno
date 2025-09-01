import { User } from '../models/User.js';

export async function createStudent(req, res) {
  const { rollNo, dob, name, department, year, phone } = req.body;
  const user = await User.create({ role: 'student', rollNo, dob, name, department, year, phone });
  res.status(201).json(user);
}

export async function createTeacher(req, res) {
  const { rollNo, dob, name, department, designation, phone } = req.body;
  const user = await User.create({ role: 'teacher', rollNo, dob, name, department, designation, phone });
  res.status(201).json(user);
}

export async function listUsers(req, res) {
  const users = await User.find().select('-passwordHash');
  res.json(users);
}
