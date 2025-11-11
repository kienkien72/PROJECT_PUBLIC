// Utility functions for Cloudflare Worker

// CORS headers
export const corsHeaders = {
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
    'Access-Control-Allow-Headers': 'Content-Type, Authorization',
};

// Success response helper
export function success(data, status = 200) {
    return new Response(JSON.stringify(data), {
        status,
        headers: { 'Content-Type': 'application/json', ...corsHeaders }
    });
}

// Error response helper
export function error(message, status = 400) {
    return new Response(JSON.stringify({ error: message }), {
        status,
        headers: { 'Content-Type': 'application/json', ...corsHeaders }
    });
}

// Generate JWT token (simplified)
export function generateToken(payload, secret) {
    const header = btoa(JSON.stringify({ alg: 'HS256', typ: 'JWT' }));
    const payload_b64 = btoa(JSON.stringify(payload));
    const signature = btoa(`${header}.${payload_b64}.${secret}`);
    return `${header}.${payload_b64}.${signature}`;
}

// Verify JWT token (simplified)
export function verifyToken(token, secret) {
    try {
        const [header, payload, signature] = token.split('.');
        const expectedSignature = btoa(`${header}.${payload}.${secret}`);

        if (signature !== expectedSignature) {
            return null;
        }

        return JSON.parse(atob(payload));
    } catch {
        return null;
    }
}

// Generate slug from name
export function generateSlug(name) {
    return name
        .toLowerCase()
        .replace(/[^a-z0-9]+/g, '-')
        .replace(/(^-|-$)/g, '');
}

// Generate order number
export function generateOrderNumber() {
    const timestamp = Date.now();
    const random = Math.floor(Math.random() * 1000);
    return `ORD-${timestamp}-${random}`;
}

// Validate email
export function isValidEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
}

// Format price
export function formatPrice(price) {
    return new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'USD'
    }).format(price);
}

// Handle file upload to R2
export async function uploadToR2(bucket, file, filename) {
    const arrayBuffer = await file.arrayBuffer();
    await bucket.put(filename, arrayBuffer);
    return `https://pub-${bucket.id}.r2.dev/${filename}`;
}

// Paginate results
export function paginate(items, page = 1, limit = 20) {
    const offset = (page - 1) * limit;
    const total = items.length;
    const pages = Math.ceil(total / limit);

    return {
        data: items.slice(offset, offset + limit),
        pagination: {
            page,
            limit,
            total,
            pages,
            hasNext: page < pages,
            hasPrev: page > 1
        }
    };
}

// Calculate cart total
export function calculateCartTotal(items) {
    const subtotal = items.reduce((sum, item) => sum + (item.unit_price * item.quantity), 0);
    const tax = subtotal * 0.08; // 8% tax
    const shipping = subtotal > 100 ? 0 : 10; // Free shipping over $100
    const total = subtotal + tax + shipping;

    return { subtotal, tax, shipping, total };
}