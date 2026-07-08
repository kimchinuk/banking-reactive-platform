CREATE TABLE IF NOT EXISTS customers (
    customer_id VARCHAR(50) PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    credit_score INTEGER NOT NULL,
    active BOOLEAN NOT NULL
);
