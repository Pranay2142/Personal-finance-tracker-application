-- SQL Migration: db/migration/V7__Create_recurring_transactions_table.sql
CREATE TABLE recurring_transactions (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
user_id BIGINT NOT NULL,
account_id BIGINT NOT NULL,
category_id BIGINT NOT NULL,
amount DECIMAL(15,2) NOT NULL,
type ENUM('INCOME', 'EXPENSE') NOT NULL,
description TEXT,
frequency ENUM('DAILY', 'WEEKLY', 'MONTHLY', 'QUARTERLY', 'YEARLY') NOT
NULL,
start_date DATE NOT NULL,
end_date DATE,
next_execution_date DATE NOT NULL,
is_active BOOLEAN DEFAULT TRUE,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE,
FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE RESTRICT
);
