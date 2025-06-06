CREATE DATABASE IF NOT EXISTS `${ORDER_DATABASE}`;
USE `${ORDER_DATABASE}`;

CREATE USER IF NOT EXISTS '${USER}'@'%' IDENTIFIED BY '${PASSWORD}';
GRANT ALL PRIVILEGES ON `${ORDER_DATABASE}`.* TO '${USER}'@'%';
FLUSH PRIVILEGES;
