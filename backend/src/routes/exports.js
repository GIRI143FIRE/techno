import express from 'express';
import { auth } from '../middleware/auth.js';
import { exportStudents } from '../controllers/exports.js';
export const router = express.Router();

router.get('/students.xlsx', auth(['teacher','admin','manager']), exportStudents);
