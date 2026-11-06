CREATE TABLE categories (
                            id BIGSERIAL PRIMARY KEY,
                            name VARCHAR(100) NOT NULL UNIQUE,
                            created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE products (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          price NUMERIC(10, 2) NOT NULL,
                          stock INTEGER NOT NULL DEFAULT 0,
                          category_id BIGINT,
                          created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                          CONSTRAINT fk_products_category
                              FOREIGN KEY (category_id)
                                  REFERENCES categories(id)
                                  ON DELETE RESTRICT
);

CREATE INDEX idx_products_category_id ON products(category_id);
CREATE INDEX idx_products_name ON products(name);