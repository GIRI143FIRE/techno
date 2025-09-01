import express from 'express';
import { auth } from '../middleware/auth.js';
import { createSession, closeSession, qrToken, scan, sessionReport } from '../controllers/attendance.js';
export const router = express.Router();

router.post('/session', auth(['teacher']), createSession);
router.post('/session/:id/close', auth(['teacher','admin']), closeSession);
router.get('/session/:id/token', auth(['teacher']), qrToken);
router.post('/scan', auth(['student']), scan);
router.get('/session/:id/report', auth(['teacher','admin']), sessionReport);
