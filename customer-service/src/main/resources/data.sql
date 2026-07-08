INSERT INTO customers (customer_id, full_name, credit_score, active) VALUES
('CUST-1001', 'Alex Johnson', 760, true),
('CUST-1002', 'Priya Shah', 640, true),
('CUST-1003', 'Chris Walker', 480, true),
('CUST-1004', 'Taylor Brown', 700, false)
ON CONFLICT (customer_id) DO NOTHING;
