CREATE TABLE IF NOT EXISTS product_catalog__products (
    id VARCHAR(255) NOT NULL primary key,
    description TEXT,
    price DECIMAL (10,2),
    picture VARCHAR(255)
);
