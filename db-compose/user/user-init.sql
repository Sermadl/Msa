CREATE DATABASE IF NOT EXISTS `user`;
USE `user`;

CREATE USER IF NOT EXISTS 'serma'@'%' IDENTIFIED BY '20021014';
GRANT ALL PRIVILEGES ON `user`.* TO 'serma'@'%';
FLUSH PRIVILEGES;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `user_role` enum('ADMIN','GUEST','SELLER','USER') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

INSERT INTO user.user (email,password,phone,username,user_role) VALUES
    ('kse@test.com','$2a$10$2QgGm/v8MghS7LLl9JQF8e08opluwF7bs2QqbSJIh4I8bp.FGQ4W.','010-9936-8036','김세은','ADMIN')