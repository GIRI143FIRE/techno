import jwt from 'jsonwebtoken';

export function auth(requiredRoles = []) {
  return (req, res, next) => {
    const hdr = req.headers.authorization || '';
    const token = hdr.startsWith('Bearer ') ? hdr.slice(7) : null;
    if (!token) return res.status(401).json({ error: 'Missing token' });
    try {
      const payload = jwt.verify(token, process.env.JWT_SECRET);
      req.user = payload;
      if (requiredRoles.length && !requiredRoles.includes(payload.role)) {
        return res.status(403).json({ error: 'Insufficient role' });
      }
      next();
    } catch (e) {
      return res.status(401).json({ error: 'Invalid token' });
    }
  }
}
