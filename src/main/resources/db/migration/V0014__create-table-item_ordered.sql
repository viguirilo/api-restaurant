CREATE TABLE `item_ordered` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `observation` varchar(255) DEFAULT NULL,
  `quantity` int NOT NULL,
  `unit_price` decimal(38,2) NOT NULL,
  `total_price` decimal(38,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `order_fk_1` (`order_id`),
  KEY `product_fk_1` (`product_id`),
  CONSTRAINT `order_fk_1` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`),
  CONSTRAINT `product_fk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci