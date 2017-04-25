CREATE TABLE phonify_phone (
  key BIGINT NOT NULL IDENTITY,
  model varchar(100) NOT NULL,
  price decimal(10,2) NOT NULL
);

CREATE TABLE phonify_order_item (
  key BIGINT NOT NULL IDENTITY,
  phone_id BIGINT NOT NULL,
  quantity BIGINT NOT NULL,
  order_id BIGINT NOT NULL
);

CREATE TABLE phonify_order (
  key BIGINT NOT NULL IDENTITY,
  subtotal decimal(10,2) NOT NULL,
  delivery_price decimal(10,2) NOT NULL,
  total_price decimal(10,2) NOT NULL,
  first_name VARCHAR(25),
  last_name VARCHAR(25),
  delivery_address VARCHAR(50),
  contact_phone_no VARCHAR(10),
  delivery_status VARCHAR(12) DEFAULT 'INITIAL' NOT NULL
);

