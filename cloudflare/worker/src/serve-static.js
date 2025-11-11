// Serve static files from Cloudflare Workers

export function handleStatic(request, env) {
    const url = new URL(request.url);
    const path = url.pathname;

    // Serve index.html for root and SPA routes
    if (path === '/' || !path.includes('.')) {
        return new Response(getIndexHTML(), {
            headers: {
                'Content-Type': 'text/html',
                'Cache-Control': 'public, max-age=3600'
            }
        });
    }

    // Serve CSS files
    if (path.endsWith('.css')) {
        return new Response(getCSS(), {
            headers: {
                'Content-Type': 'text/css',
                'Cache-Control': 'public, max-age=86400'
            }
        });
    }

    // Serve JS files
    if (path.endsWith('.js')) {
        return new Response(getJS(), {
            headers: {
                'Content-Type': 'application/javascript',
                'Cache-Control': 'public, max-age=86400'
            }
        });
    }

    // 404 for other files
    return new Response('Not Found', { status: 404 });
}

function getIndexHTML() {
    return `<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Laptop Shop - Your Trusted Laptop Store</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <style>${getCSS()}</style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top">
        <div class="container">
            <a class="navbar-brand" href="/">
                <i class="fas fa-laptop"></i> Laptop Shop
            </a>
        </div>
    </nav>

    <section class="hero bg-primary text-white py-5">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-lg-6">
                    <h1 class="display-4 fw-bold mb-4">Find Your Perfect Laptop</h1>
                    <p class="lead mb-4">Gaming, Business, or Everyday Use - We have it all with unbeatable prices</p>
                    <div class="d-flex gap-3">
                        <button class="btn btn-light btn-lg" onclick="alert('Site deployed on Cloudflare Workers!')">
                            <i class="fas fa-shopping-bag"></i> Shop Now
                        </button>
                    </div>
                </div>
                <div class="col-lg-6">
                    <img src="https://images.unsplash.com/photo-1496181133206-80ce9b88a853?auto=format&fit=crop&w=800"
                         alt="Featured Laptop" class="img-fluid rounded shadow">
                </div>
            </div>
        </div>
    </section>

    <section class="py-5">
        <div class="container">
            <h2 class="text-center mb-5">Featured Products</h2>
            <div class="row" id="products">
                <div class="col-md-4 mb-4">
                    <div class="card h-100">
                        <img src="https://images.unsplash.com/photo-1496181133206-80ce9b88a853" class="card-img-top" alt="Laptop">
                        <div class="card-body">
                            <h5 class="card-title">Gaming Laptop Pro</h5>
                            <p class="card-text">High-performance gaming laptop with RTX graphics</p>
                            <h3 class="text-primary">$1,299</h3>
                            <button class="btn btn-primary">Add to Cart</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <script>${getJS()}</script>
</body>
</html>`;
}

function getCSS() {
    return `
        :root {
            --primary-color: #2c3e50;
            --secondary-color: #3498db;
        }

        .hero {
            background: linear-gradient(135deg, var(--primary-color) 0%, var(--secondary-color) 100%);
            min-height: 500px;
            display: flex;
            align-items: center;
        }

        .card:hover {
            transform: translateY(-5px);
            transition: transform 0.3s;
        }

        .card img {
            height: 200px;
            object-fit: cover;
        }
    `;
}

function getJS() {
    return `
        console.log('Laptop Shop deployed on Cloudflare Workers!');

        // Basic interactivity
        document.addEventListener('DOMContentLoaded', function() {
            console.log('Page loaded successfully!');
        });
    `;
}