import express from 'express';
import { auth } from '../middleware/auth.js';
import { me, updateMe, listStudents } from '../controllers/students.js';
export const router = express.Router();

router.get('/me', auth(['student','teacher','admin','manager']), me);
router.patch('/me', auth(['student','teacher']), updateMe);
router.get('/', auth(['teacher','admin','manager']), listStudents);
