import express from 'express';
import { auth } from '../middleware/auth.js';
import { upsertFees, myFees } from '../controllers/fees.js';
export const router = express.Router();

router.post('/', auth(['manager','admin']), upsertFees);
router.get('/me', auth(['student']), myFees);
