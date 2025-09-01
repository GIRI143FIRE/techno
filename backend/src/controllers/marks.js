import { Mark } from '../models/Mark.js';

export async function addMark(req, res) {
  const { studentId, subject, marks, maxMarks, examType, date } = req.body;
  const item = await Mark.create({ student: studentId, subject, marks, maxMarks, examType, date, enteredBy: req.user.id });
  res.status(201).json(item);
}

export async function myMarks(req, res) {
  const list = await Mark.find({ student: req.user.id }).sort({ date: -1 });
  res.json(list);
}

export async function marksForStudent(req, res) {
  const { id } = req.params;
  const list = await Mark.find({ student: id }).sort({ date: -1 });
  res.json(list);
}
