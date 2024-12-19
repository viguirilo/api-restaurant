CREATE TABLE `city` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `state_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `state_fk_1` (`state_id`),
  CONSTRAINT `state_fk_1` FOREIGN KEY (`state_id`) REFERENCES `state` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci