import { Fees } from '../models/Fees.js';

export async function upsertFees(req, res) {
  const { studentId, items } = req.body;
  const doc = await Fees.findOneAndUpdate(
    { student: studentId },
    { student: studentId, items, updatedBy: req.user.id },
    { upsert: true, new: true }
  );
  res.json(doc);
}

export async function myFees(req, res) {
  const fees = await Fees.findOne({ student: req.user.id });
  res.json(fees || { items: [] });
}
