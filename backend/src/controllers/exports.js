import ExcelJS from 'exceljs';
import { User } from '../models/User.js';
import { fileURLToPath } from 'url';
import path from 'path';
import fs from 'fs';

export async function exportStudents(req, res) {
  const workbook = new ExcelJS.Workbook();
  const sheet = workbook.addWorksheet('Students');
  sheet.columns = [
    { header: 'Roll No', key: 'rollNo', width: 15 },
    { header: 'Name', key: 'name', width: 25 },
    { header: 'Department', key: 'department', width: 20 },
    { header: 'Year', key: 'year', width: 10 },
    { header: 'Phone', key: 'phone', width: 15 },
    { header: 'Email', key: 'email', width: 25 },
  ];
  const rows = await User.find({ role: 'student' });
  rows.forEach(r => sheet.addRow({
    rollNo: r.rollNo, name: r.name, department: r.department, year: r.year, phone: r.phone, email: r.email
  }));
  const tmpPath = path.join(process.cwd(), 'students_export.xlsx');
  await workbook.xlsx.writeFile(tmpPath);
  res.download(tmpPath, 'students.xlsx', (err) => {
    try { fs.unlinkSync(tmpPath); } catch {}
  });
}
