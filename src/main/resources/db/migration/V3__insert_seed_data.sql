INSERT INTO categories (name) VALUES
                                  ('Hardware'),
                                  ('Accesorios');

INSERT INTO tags (name) VALUES
                            ('Gamer'),
                            ('Oficina'),
                            ('Wireless');

INSERT INTO products (name, price, stock, category_id) VALUES
                                                           ('Teclado Mecánico', 79.99, 20, 1),
                                                           ('Mouse Inalámbrico', 34.50, 45, 2);

INSERT INTO product_tags (product_id, tag_id) VALUES
                                                  (1, 1),
                                                  (2, 2),
                                                  (2, 3);