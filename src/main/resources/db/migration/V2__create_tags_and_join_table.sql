CREATE TABLE tags (
                      id BIGSERIAL PRIMARY KEY,
                      name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE product_tags (
                              product_id BIGINT NOT NULL,
                              tag_id BIGINT NOT NULL,
                              CONSTRAINT pk_product_tags PRIMARY KEY (product_id, tag_id),
                              CONSTRAINT fk_pt_product
                                  FOREIGN KEY (product_id)
                                      REFERENCES products(id)
                                      ON DELETE CASCADE,
                              CONSTRAINT fk_pt_tag
                                  FOREIGN KEY (tag_id)
                                      REFERENCES tags(id)
                                      ON DELETE CASCADE
);

CREATE INDEX idx_pt_product_id ON product_tags(product_id);
CREATE INDEX idx_pt_tag_id ON product_tags(tag_id);