import { User } from '../models/User.js';

export async function myClasses(req, res) {
  // placeholder - extend with real scheduling
  res.json([{ courseCode: 'CS101', section: 'A' }, { courseCode: 'MA102', section: 'B' }]);
}
