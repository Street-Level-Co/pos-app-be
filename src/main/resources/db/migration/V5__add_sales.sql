ALTER TABLE client ALTER COLUMN username DROP NOT NULL;

CREATE TABLE sale (
                       sale_id UUID NOT NULL DEFAULT gen_random_uuid(),
                       org_id UUID NOT NULL,
                       client_id UUID,
                       total_amount NUMERIC(19, 2) NOT NULL,
                       created_at TIMESTAMP NOT NULL DEFAULT now(),
                       CONSTRAINT pk_sale PRIMARY KEY (sale_id),
                       CONSTRAINT fk_sale_org FOREIGN KEY (org_id)
                           REFERENCES organization (org_id),
                       CONSTRAINT fk_sale_client FOREIGN KEY (client_id)
                           REFERENCES client (client_id)
);

CREATE INDEX idx_sale_org_id ON sale (org_id);

CREATE TABLE sale_item (
                            sale_item_id UUID NOT NULL DEFAULT gen_random_uuid(),
                            sale_id UUID NOT NULL,
                            catalog_item_id UUID NOT NULL,
                            qty INTEGER NOT NULL,
                            price NUMERIC(19, 2) NOT NULL,
                            discount_price NUMERIC(19, 2),
                            CONSTRAINT pk_sale_item PRIMARY KEY (sale_item_id),
                            CONSTRAINT fk_sale_item_sale FOREIGN KEY (sale_id)
                                REFERENCES sale (sale_id),
                            CONSTRAINT fk_sale_item_catalog_item FOREIGN KEY (catalog_item_id)
                                REFERENCES catalog_item (catalog_item_id)
);

CREATE INDEX idx_sale_item_sale_id ON sale_item (sale_id);
