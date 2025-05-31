CREATE TABLE brands (
    brand_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(50) NOT NULL
);

CREATE TABLE prices (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    brand_id BIGINT NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    price_list INT NOT NULL,
    product_id BIGINT NOT NULL,
    priority INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    curr CHAR(3) NOT NULL,
    CONSTRAINT fk_brand FOREIGN KEY (brand_id) REFERENCES brands(brand_id)
);
