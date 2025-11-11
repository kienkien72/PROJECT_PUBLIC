// Authentication middleware for Cloudflare Worker

import { verifyToken, error } from '../utils.js';

// Middleware to check if user is authenticated
export async function requireAuth(request, env) {
    const authHeader = request.headers.get('Authorization');

    if (!authHeader || !authHeader.startsWith('Bearer ')) {
        return error('Authentication required', 401);
    }

    const token = authHeader.substring(7);
    const payload = verifyToken(token, env.JWT_SECRET);

    if (!payload) {
        return error('Invalid or expired token', 401);
    }

    // Add user info to request
    request.user = payload;
    return null; // Continue to next handler
}

// Middleware to check if user is admin
export async function requireAdmin(request, env) {
    const authResult = await requireAuth(request, env);
    if (authResult) return authResult;

    if (request.user.role !== 'ADMIN') {
        return error('Admin access required', 403);
    }

    return null;
}

// Get user from request (optional auth)
export async function getUser(request, env) {
    const authHeader = request.headers.get('Authorization');

    if (!authHeader || !authHeader.startsWith('Bearer ')) {
        return null;
    }

    const token = authHeader.substring(7);
    return verifyToken(token, env.JWT_SECRET);
}