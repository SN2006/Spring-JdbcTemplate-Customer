CREATE DATABASE demo_db;

CREATE TABLE IF NOT EXISTS customers(
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(256) NOT NULL,
    phone VARCHAR(64) NOT NULL,
    address VARCHAR(256) NOT NULL,
    PRIMARY KEY (id)
)