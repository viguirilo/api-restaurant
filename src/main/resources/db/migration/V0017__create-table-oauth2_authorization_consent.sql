CREATE TABLE `oauth2_authorization_consent` (
    `registered_client_id` varchar(100) NOT NULL,
    `principal_name` varchar(200) NOT NULL,
    `authorities` varchar(1000) NOT NULL,
    PRIMARY KEY (`registered_client_id`, `principal_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;