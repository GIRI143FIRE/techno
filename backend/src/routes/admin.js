import express from 'express';
import { auth } from '../middleware/auth.js';
import { createStudent, createTeacher, listUsers } from '../controllers/admin.js';
export const router = express.Router();

router.post('/students', auth(['admin','manager']), createStudent);
router.post('/teachers', auth(['admin','manager']), createTeacher);
router.get('/users', auth(['admin','manager']), listUsers);
