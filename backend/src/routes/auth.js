import express from 'express';
import { studentLogin, teacherLogin, adminLogin } from '../controllers/auth.js';
export const router = express.Router();

router.post('/student/login', studentLogin);
router.post('/teacher/login', teacherLogin);
router.post('/admin/login', adminLogin);
