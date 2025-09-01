import express from 'express';
import { auth } from '../middleware/auth.js';
import { addMark, myMarks, marksForStudent } from '../controllers/marks.js';
export const router = express.Router();

router.post('/', auth(['teacher']), addMark);
router.get('/me', auth(['student']), myMarks);
router.get('/student/:id', auth(['teacher','admin']), marksForStudent);
