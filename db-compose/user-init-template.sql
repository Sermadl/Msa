CREATE DATABASE IF NOT EXISTS `${USER_DATABASE}`;
USE `${USER_DATABASE}`;

-- CREATE TABLE IF NOT EXISTS `${USER_TABLE}` (
--     `id` bigint(20) NOT NULL AUTO_INCREMENT,
--     `email` varchar(255) DEFAULT NULL,
--     `password` varchar(255) DEFAULT NULL,
--     `phone` varchar(255) DEFAULT NULL,
--     `username` varchar(255) DEFAULT NULL,
--     PRIMARY KEY (`id`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
--
-- CREATE TABLE IF NOT EXISTS federated_item (
--     id BIGINT(20) NOT NULL AUTO_INCREMENT,
--     quantity INT(11) NOT NULL,
--     seller_id BIGINT(20) DEFAULT NULL,
--     description VARCHAR(255) DEFAULT NULL,
--     name VARCHAR(255) DEFAULT NULL,
--     PRIMARY KEY (id)
-- ) ENGINE=FEDERATED
-- CONNECTION='mysql://${USER}:${PASSWORD}@host.docker.internal:3309/item/item';
--
-- CREATE TABLE IF NOT EXISTS federated_orders (
--     id BIGINT(20) NOT NULL AUTO_INCREMENT,
--     address VARCHAR(255) DEFAULT NULL,
--     customer_id BIGINT(20) DEFAULT NULL,
--     description VARCHAR(255) DEFAULT NULL,
--     item_id BIGINT(20) DEFAULT NULL,
--     order_date DATETIME(6) DEFAULT NULL,
--     quantity INT(11) NOT NULL,
--     PRIMARY KEY (id)
-- ) ENGINE=FEDERATED
-- CONNECTION='mysql://${USER}:${PASSWORD}@host.docker.internal:3307/orders/orders';

CREATE USER IF NOT EXISTS '${USER}'@'%' IDENTIFIED BY '${PASSWORD}';
GRANT ALL PRIVILEGES ON `${USER_DATABASE}`.* TO '${USER}'@'%';
FLUSH PRIVILEGES;
