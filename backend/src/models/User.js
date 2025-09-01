import mongoose from 'mongoose';

const options = { timestamps: true };

const addressSchema = new mongoose.Schema({
  line1: String,
  line2: String,
  city: String,
  state: String,
  pincode: String,
});

const userSchema = new mongoose.Schema({
  role: { type: String, enum: ['student','teacher','admin','manager'], required: true },
  rollNo: { type: String, index: true }, // required for student/teacher login
  dob: { type: String }, // store as YYYY-MM-DD for simple login (hash for prod)
  email: { type: String, lowercase: true, trim: true },
  passwordHash: { type: String }, // for admin/manager email login
  name: { type: String, required: true },
  department: String,
  phone: String,
  avatarUrl: String,
  address: addressSchema,
  year: Number, // student year (1-4)
  guardian: {
    name: String,
    phone: String,
    email: String
  },
  // For teachers
  designation: String,
}, options);

export const User = mongoose.model('User', userSchema);
