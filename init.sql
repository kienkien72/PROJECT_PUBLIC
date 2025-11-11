-- Create initial data for Laptop Shop
USE laptopshop;

-- Insert roles
INSERT INTO roles (id, name, description) VALUES
(1, 'ADMIN', 'Administrator role'),
(2, 'USER', 'Regular user role')
ON DUPLICATE KEY UPDATE name=VALUES(name);

-- Insert admin user (password: admin)
INSERT INTO users (fullname, email, password, role_id, address, phone) VALUES
('Administrator', 'admin@admin.com', '$2a$10$bI1QpM7zedT8oi8AZI6GO.guKuANxnKPdUQ3nEQcdcOpHtldufus.', 1, 'Admin Address', '0123456789')
ON DUPLICATE KEY UPDATE password=VALUES(password);

-- Insert sample products
INSERT INTO products (name, image, avatar, price, quantity, sold, factory, target, short_desc, detail_desc) VALUES
('MacBook Pro 14 inch M3', 'macbook-pro-14.png', 'macbook-pro-14.png', 2499.00, 15, 0, 'Apple', 'Professional', 'Powerful MacBook with M3 chip', 'Apple MacBook Pro 14-inch with M3 Pro chip, 18GB RAM, 512GB SSD. Liquid Retina XDR display.'),

('MacBook Air 15 inch M2', 'macbook-air-15.png', 'macbook-air-15.png', 1299.00, 25, 0, 'Apple', 'Personal', 'Thin and lightweight laptop', 'Apple MacBook Air 15-inch with M2 chip, 8GB RAM, 256GB SSD. Stunning Liquid Retina display.'),

('Dell XPS 15', 'dell-xps-15.png', 'dell-xps-15.png', 1899.00, 20, 0, 'Dell', 'Professional', 'Premium Windows laptop', 'Dell XPS 15 with Intel Core i7-13700H, 16GB DDR5 RAM, 512GB SSD, NVIDIA RTX 4050.'),

('Dell Inspiron 16', 'dell-inspiron-16.png', 'dell-inspiron-16.png', 899.00, 30, 0, 'Dell', 'Student', 'Affordable large screen laptop', 'Dell Inspiron 16 with Intel Core i5, 12GB RAM, 512GB SSD. 16-inch FHD+ display.'),

('Lenovo ThinkPad X1 Carbon', 'lenovo-thinkpad-x1.png', 'lenovo-thinkpad-x1.png', 1599.00, 18, 0, 'Lenovo', 'Business', 'Ultra-thin business laptop', 'Lenovo ThinkPad X1 Carbon Gen 11 with Intel Core i7, 16GB RAM, 1TB SSD. Military-grade durability.'),

('ASUS ROG Zephyrus G14', 'asus-rog-zephyrus.png', 'asus-rog-zephyrus.png', 1699.00, 12, 0, 'ASUS', 'Gaming', 'High-performance gaming laptop', 'ASUS ROG Zephyrus G14 with AMD Ryzen 9, 32GB RAM, 1TB SSD, RTX 4060. 14-inch QHD display.'),

('ASUS ZenBook Pro', 'asus-zenbook.png', 'asus-zenbook.png', 1199.00, 22, 0, 'ASUS', 'Professional', 'Creator-friendly laptop', 'ASUS ZenBook Pro with Intel Core i7, 16GB RAM, 512GB SSD. 4K OLED display.'),

('Acer Nitro 5', 'acer-nitro-5.png', 'acer-nitro-5.png', 999.00, 35, 0, 'Acer', 'Gaming', 'Budget gaming laptop', 'Acer Nitro 5 with Intel Core i5, 8GB RAM, 512GB SSD, RTX 3050. 15.6-inch FHD 144Hz display.'),

('LG Gram 17', 'lg-gram-17.png', 'lg-gram-17.png', 1399.00, 15, 0, 'LG', 'Professional', 'Lightweight large screen', 'LG Gram 17 with Intel Core i7, 16GB RAM, 1TB SSD. 17-inch FHD display, weighs only 2.98lbs.'),

('HP Spectre x360', 'hp-spectre-x360.png', 'hp-spectre-x360.png', 1449.00, 20, 0, 'HP', 'Professional', '2-in-1 convertible laptop', 'HP Spectre x360 with Intel Core i7, 16GB RAM, 512GB SSD. 13.5-inch 3K2K display, tablet mode.'),

('iPad Pro 12.9 inch', 'ipad-pro-129.png', 'ipad-pro-129.png', 1099.00, 25, 0, 'Apple', 'Creative', 'Professional tablet', 'Apple iPad Pro 12.9-inch with M2 chip, 128GB, Wi-Fi 6E.'),

('Microsoft Surface Pro 9', 'microsoft-surface.png', 'microsoft-surface.png', 999.00, 28, 0, 'Microsoft', 'Business', 'Tablet-laptop hybrid', 'Microsoft Surface Pro 9 with Intel Core i5, 8GB RAM, 256GB SSD.')
ON DUPLICATE KEY UPDATE name=VALUES(name);