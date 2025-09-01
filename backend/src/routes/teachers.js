import express from 'express';
import { auth } from '../middleware/auth.js';
import { myClasses } from '../controllers/teachers.js';
export const router = express.Router();

router.get('/classes', auth(['teacher']), myClasses);
