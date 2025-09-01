import mongoose from 'mongoose';

const marksSchema = new mongoose.Schema({
  student: { type: mongoose.Schema.Types.ObjectId, ref: 'User', required: true },
  subject: { type: String, required: true },
  examType: { type: String, enum: ['internal','semester','quiz','assignment'], default: 'internal' },
  marks: { type: Number, required: true },
  maxMarks: { type: Number, required: true },
  date: { type: Date, default: Date.now },
  enteredBy: { type: mongoose.Schema.Types.ObjectId, ref: 'User' },
}, { timestamps: true });

export const Mark = mongoose.model('Mark', marksSchema);
