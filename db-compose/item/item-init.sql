CREATE DATABASE IF NOT EXISTS `item`;
USE `item`;

CREATE USER IF NOT EXISTS 'serma'@'%' IDENTIFIED BY '20021014';
GRANT ALL PRIVILEGES ON `item`.* TO 'serma'@'%';
FLUSH PRIVILEGES;

CREATE TABLE `item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `quantity` int(11) NOT NULL,
  `seller_id` bigint(20) DEFAULT NULL,
  `price` decimal(38,2) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

INSERT INTO item.item (description,name,quantity,seller_id,price,created_at,updated_at,category_id) VALUES
	 ('Apple Silicon M3 14인치 18G RAM','맥북',100,1,3000000.00,NULL,NULL,10),
	 ('스타벅스 자몽허니블랙티','자몽허니블랙티',100,1,5700.00,NULL,NULL,1),
	 ('test','테스트',100,1,12345.00,NULL,NULL,34);

CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_category` (`parent_id`),
  CONSTRAINT `FK_category` FOREIGN KEY (`parent_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO item.category (name,parent_id) VALUES
	 ('FOOD',NULL),
	 ('FASHION',NULL),
	 ('ELECTRONICS',NULL),
	 ('BEAUTY',NULL),
	 ('TOOLS',NULL),
	 ('FROZEN FOOD',1),
	 ('MEN''S  FASHION',2),
	 ('WOMEN''S  FASHION',2),
	 ('KID''S  FASHION',2),
	 ('COMPUTER',3);
INSERT INTO item.category (name,parent_id) VALUES
	 ('TV',3),
	 ('PHONE',3),
	 ('GAME',3),
	 ('KEYBOARD',3),
	 ('HEALTH',3),
	 ('WASHER/DRIER',3),
	 ('SKIN CARE',4),
	 ('CLEANSING',4),
	 ('MAKE-UP',4),
	 ('BODY',4);
INSERT INTO item.category (name,parent_id) VALUES
	 ('HAIR',4),
	 ('FOR-MEN',4),
	 ('KITCHEN',5),
	 ('CAR',5),
	 ('PET',5),
	 ('WRITING/OFFICE',5),
	 ('TOY',5),
	 ('HEALTH',1),
	 ('LIVING',5),
	 ('HOME DECO',5);
INSERT INTO item.category (name,parent_id) VALUES
	 ('BOOK',NULL),
	 ('ECONOMY',31),
	 ('SELF-IMPROVE',31),
	 ('ETC',NULL);