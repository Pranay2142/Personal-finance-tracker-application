-- SQL Migration: db/migration/V7__Create_transactions_table.sql
CREATE TABLE transactions (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
user_id BIGINT NOT NULL,
account_id BIGINT NOT NULL,
category_id BIGINT NOT NULL,
amount DECIMAL(15,2) NOT NULL,
type ENUM('INCOME', 'EXPENSE') NOT NULL,
description TEXT,
transaction_date DATE NOT NULL,
recurring_transaction_id BIGINT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE,
FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE RESTRICT,
FOREIGN KEY (recurring_transaction_id) REFERENCES recurring_transactions(id) ON DELETE SET NULL

);