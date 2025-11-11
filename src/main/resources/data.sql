-- Insert initial data for PostgreSQL

-- Insert roles
INSERT INTO role (id, name) VALUES
(1, 'ADMIN'),
(2, 'USER') ON CONFLICT (id) DO NOTHING;

-- Insert admin user with BCrypt hashed password for 'admin'
INSERT INTO user (id, email, password, full_name, role_id) VALUES
(1, 'admin@admin.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9P8jskjlj3M/HoS', 'Administrator', 1)
ON CONFLICT (id) DO NOTHING;

-- Insert admin user with BCrypt hashed password for '123456'
INSERT INTO user (id, email, password, full_name, role_id) VALUES
(2, 'admin@admin.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Administrator', 1)
ON CONFLICT (id) DO NOTHING;