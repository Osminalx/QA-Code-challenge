-- Drop order_line_items first due to foreign key constraint
--DROP TABLE IF EXISTS order_line_items;

-- Drop orders table after
--DROP TABLE IF EXISTS orders;


create table if not exists user_messages (
    id SERIAL PRIMARY KEY,
    message VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);