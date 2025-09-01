import mongoose from 'mongoose';

const feeItemSchema = new mongoose.Schema({
  type: { type: String, enum: ['college','hostel','bus','other'], required: true },
  year: { type: Number, required: true },
  amount: { type: Number, required: true },
  status: { type: String, enum: ['due','paid','partial'], default: 'due' },
  dueDate: { type: Date },
  notes: String
});

const feesSchema = new mongoose.Schema({
  student: { type: mongoose.Schema.Types.ObjectId, ref: 'User', required: true },
  items: [feeItemSchema],
  updatedBy: { type: mongoose.Schema.Types.ObjectId, ref: 'User' },
}, { timestamps: true });

export const Fees = mongoose.model('Fees', feesSchema);
