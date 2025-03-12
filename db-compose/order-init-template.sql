CREATE DATABASE IF NOT EXISTS `${ORDER_DATABASE}`;
USE `${ORDER_DATABASE}`;

CREATE TABLE IF NOT EXISTS `${ORDER_TABLE}` (
      `quantity` int(11) NOT NULL,
      `customer_id` bigint(20) DEFAULT NULL,
      `id` bigint(20) NOT NULL AUTO_INCREMENT,
      `item_id` bigint(20) DEFAULT NULL,
      `order_date` datetime(6) DEFAULT NULL,
      `address` varchar(255) DEFAULT NULL,
      `description` varchar(255) DEFAULT NULL,
      PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE USER IF NOT EXISTS '${USER}'@'%' IDENTIFIED BY '${PASSWORD}';
GRANT ALL PRIVILEGES ON `${ORDER_DATABASE}`.* TO '${USER}'@'%';
FLUSH PRIVILEGES;
