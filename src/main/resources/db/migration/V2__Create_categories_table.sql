-- SQL Migration: db/migration/V2__Create_categories_table.sql
CREATE TABLE categories (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
user_id BIGINT NOT NULL,
name VARCHAR(100) NOT NULL,
type ENUM('INCOME', 'EXPENSE') NOT NULL,
color VARCHAR(7), -- Hex color code
icon VARCHAR(50),
is_active BOOLEAN DEFAULT TRUE,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
UNIQUE KEY unique_user_category (user_id, name, type)
);
