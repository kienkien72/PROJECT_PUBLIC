// API Configuration for Cloudflare Worker Backend

// Update this with your actual Worker URL after deployment
const API_BASE_URL = "https://laptopshop-.dev";

// Store current user info
let currentUser = null;
let authToken = null;

// API helper functions
class API {
  // Generic request method
  static async request(endpoint, options = {}) {
    const url = `${API_BASE_URL}${endpoint}`;
    const config = {
      headers: {
        "Content-Type": "application/json",
        ...options.headers,
      },
      ...options,
    };

    // Add auth token if available
    if (authToken) {
      config.headers.Authorization = `Bearer ${authToken}`;
    }

    try {
      const response = await fetch(url, config);
      const data = await response.json();

      if (!response.ok) {
        throw new Error(data.error || "Request failed");
      }

      return data;
    } catch (error) {
      console.error("API Error:", error);
      throw error;
    }
  }

  // Auth methods
  static async login(email, password) {
    const data = await this.request("/api/login", {
      method: "POST",
      body: JSON.stringify({ email, password }),
    });

    // Store token and user info
    authToken = generateToken(data);
    currentUser = data;
    localStorage.setItem("authToken", authToken);
    localStorage.setItem("currentUser", JSON.stringify(data));

    return data;
  }

  static async register(userData) {
    const data = await this.request("/api/register", {
      method: "POST",
      body: JSON.stringify(userData),
    });

    return data;
  }

  // Product methods
  static async getProducts(page = 1, category = "", sort = "name") {
    const params = new URLSearchParams({
      page,
      limit: 12,
    });

    if (category) params.append("category", category);
    if (sort) params.append("sort", sort);

    const data = await this.request(`/api/products?${params}`);
    return data;
  }

  static async getProduct(id) {
    const data = await this.request(`/api/products/${id}`);
    return data;
  }

  static async searchProducts(query) {
    const data = await this.request(
      `/api/products/search?q=${encodeURIComponent(query)}`
    );
    return data;
  }

  // Cart methods
  static async addToCart(productId, quantity = 1) {
    if (!currentUser) {
      throw new Error("Please login to add items to cart");
    }

    const data = await this.request("/api/cart/add", {
      method: "POST",
      body: JSON.stringify({
        userId: currentUser.id,
        productId,
        quantity,
      }),
    });

    return data;
  }

  static async getCart() {
    if (!currentUser) return [];

    const data = await this.request(`/api/cart/${currentUser.id}`);
    return data;
  }

  static async updateCartItem(itemId, quantity) {
    const data = await this.request("/api/cart/update", {
      method: "PUT",
      body: JSON.stringify({ itemId, quantity }),
    });

    return data;
  }

  static async removeFromCart(itemId) {
    const data = await this.request(`/api/cart/remove/${itemId}`, {
      method: "DELETE",
    });

    return data;
  }

  // Order methods
  static async createOrder(orderData) {
    if (!currentUser) {
      throw new Error("Please login to place an order");
    }

    const data = await this.request("/api/orders", {
      method: "POST",
      body: JSON.stringify({
        userId: currentUser.id,
        ...orderData,
      }),
    });

    return data;
  }

  static async getOrders() {
    if (!currentUser) return [];

    const data = await this.request(`/api/orders/user/${currentUser.id}`);
    return data;
  }

  static async getOrder(orderId) {
    const data = await this.request(`/api/orders/${orderId}`);
    return data;
  }

  // Review methods
  static async getProductReviews(productId) {
    const data = await this.request(`/api/reviews/product/${productId}`);
    return data;
  }

  static async addReview(productId, reviewData) {
    if (!currentUser) {
      throw new Error("Please login to add a review");
    }

    const data = await this.request("/api/reviews", {
      method: "POST",
      body: JSON.stringify({
        productId,
        userId: currentUser.id,
        ...reviewData,
      }),
    });

    return data;
  }

  // Wishlist methods
  static async addToWishlist(productId) {
    if (!currentUser) {
      throw new Error("Please login to add to wishlist");
    }

    const data = await this.request("/api/wishlist/add", {
      method: "POST",
      body: JSON.stringify({
        userId: currentUser.id,
        productId,
      }),
    });

    return data;
  }

  static async getWishlist() {
    if (!currentUser) return [];

    const data = await this.request(`/api/wishlist/${currentUser.id}`);
    return data;
  }

  static async removeFromWishlist(productId) {
    const data = await this.request(`/api/wishlist/remove/${productId}`, {
      method: "DELETE",
    });

    return data;
  }
}

// Simple JWT-like token generation for client-side storage
function generateToken(user) {
  const payload = {
    id: user.id,
    email: user.email,
    role: user.role,
    exp: Date.now() + 24 * 60 * 60 * 1000, // 24 hours
  };
  return btoa(JSON.stringify(payload));
}

// Load saved user session on page load
function loadUserSession() {
  const savedToken = localStorage.getItem("authToken");
  const savedUser = localStorage.getItem("currentUser");

  if (savedToken && savedUser) {
    try {
      const payload = JSON.parse(atob(savedToken));
      if (payload.exp > Date.now()) {
        authToken = savedToken;
        currentUser = JSON.parse(savedUser);
        updateUserUI();
      } else {
        // Token expired
        logout();
      }
    } catch {
      logout();
    }
  }
}

// Logout function
function logout() {
  currentUser = null;
  authToken = null;
  localStorage.removeItem("authToken");
  localStorage.removeItem("currentUser");
  updateUserUI();
  window.location.reload();
}

// Update UI based on login status
function updateUserUI() {
  const accountDropdown = document.querySelector(".navbar-nav .dropdown-menu");

  if (currentUser) {
    accountDropdown.innerHTML = `
            <li><a class="dropdown-item" href="#" onclick="showProfile()">
                <i class="fas fa-user"></i> Profile
            </a></li>
            <li><a class="dropdown-item" href="#" onclick="showOrders()">
                <i class="fas fa-box"></i> My Orders
            </a></li>
            <li><a class="dropdown-item" href="#" onclick="showWishlist()">
                <i class="fas fa-heart"></i> Wishlist
            </a></li>
            <li><hr class="dropdown-divider"></li>
            <li><a class="dropdown-item" href="#" onclick="logout()">
                <i class="fas fa-sign-out-alt"></i> Logout
            </a></li>
        `;
  } else {
    accountDropdown.innerHTML = `
            <li><a class="dropdown-item" href="#" onclick="showLogin()">
                <i class="fas fa-sign-in-alt"></i> Login
            </a></li>
            <li><a class="dropdown-item" href="#" onclick="showRegister()">
                <i class="fas fa-user-plus"></i> Register
            </a></li>
        `;
  }
}

// Initialize on page load
document.addEventListener("DOMContentLoaded", loadUserSession);
