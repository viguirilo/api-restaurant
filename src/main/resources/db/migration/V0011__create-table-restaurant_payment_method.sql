CREATE TABLE `restaurant_payment_method` (
  `restaurant_id` bigint NOT NULL,
  `payment_method_id` bigint NOT NULL,
  KEY `payment_method_fk_1` (`payment_method_id`),
  KEY `restaurant_fk_2` (`restaurant_id`),
  CONSTRAINT `payment_method_fk_1` FOREIGN KEY (`payment_method_id`) REFERENCES `payment_method` (`id`),
  CONSTRAINT `restaurant_fk_2` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;