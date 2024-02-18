-- Create the database
CREATE DATABASE IF NOT EXISTS inventory_system;
USE inventory_system;

-- Create the raw_materials table
CREATE TABLE IF NOT EXISTS raw_materials (
    material_id INT PRIMARY KEY AUTO_INCREMENT,
    material_name VARCHAR(100) NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL
);
-- Insert sample data into raw_materials
INSERT INTO raw_materials (material_name, quantity, unit_price) VALUES
('Material1', 100, 10.5),
('Material2', 200, 8.75),
('Material3', 150, 12.0);

-- Create the customers table
CREATE TABLE IF NOT EXISTS customers (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20)
);

-- Insert sample data into customers
INSERT INTO customers (customer_name, email, phone_number) VALUES
('Customer1', 'customer1@email.com', '123-456-7890'),
('Customer2', 'customer2@email.com', '987-654-3210');

-- Create the orders table
CREATE TABLE IF NOT EXISTS orders (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    order_date DATE NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

-- Insert sample data into orders
INSERT INTO orders (customer_id, order_date, total_amount) VALUES
(1, '2022-01-01', 50.0),
(2, '2022-02-15', 75.5);

-- Create the products table
CREATE TABLE IF NOT EXISTS products (
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(100) NOT NULL,
    description TEXT,
    unit_price DECIMAL(10, 2) NOT NULL
);

-- Insert sample data into products
INSERT INTO products (product_name, description, unit_price) VALUES
('Product1', 'Description for Product1', 25.0),
('Product2', 'Description for Product2', 30.5);

-- Create the order_details table
CREATE TABLE IF NOT EXISTS order_details (
    order_detail_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT,
    product_id INT,
    quantity INT NOT NULL,
    subtotal DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- Insert sample data into order_details
INSERT INTO order_details (order_id, product_id, quantity, subtotal) VALUES
(1, 1, 2, 50.0),
(2, 2, 3, 91.5);

-- Create the 'users' table
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL
);

-- Insert sample user data
INSERT INTO users (username, password) VALUES
('admin', 'admin123'),  -- Sample admin user
('user1', 'password1'),  -- Sample user 1
('user2', 'password2');  -- Sample user 2
