import dotenv from 'dotenv';
import mongoose from 'mongoose';
import bcrypt from 'bcryptjs';
import { User } from '../models/User.js';

dotenv.config();
await mongoose.connect(process.env.MONGO_URI, { dbName: 'college_attendance' });

async function run() {
  await User.deleteMany({ email: 'admin@college.edu' });
  const pwd = await bcrypt.hash('admin123', 10);
  await User.create({
    role: 'admin',
    email: 'admin@college.edu',
    passwordHash: pwd,
    name: 'College Admin'
  });
  await User.create({
    role: 'manager',
    email: 'manager@college.edu',
    passwordHash: await bcrypt.hash('manager123', 10),
    name: 'College Manager'
  });
  console.log('Seeded admin and manager.');
  process.exit(0);
}
run();
