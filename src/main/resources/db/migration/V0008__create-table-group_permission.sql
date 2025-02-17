CREATE TABLE `group_permission` (
  `group_id` bigint NOT NULL,
  `permission_id` bigint NOT NULL,
  KEY `group_fk_1` (`group_id`),
  KEY `permission_fk_1` (`permission_id`),
  CONSTRAINT `group_fk_1` FOREIGN KEY (`group_id`) REFERENCES `group` (`id`),
  CONSTRAINT `permission_fk_1` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;