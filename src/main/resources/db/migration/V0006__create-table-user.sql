CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fullname` varchar(80) NOT NULL,
  `username` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `language_code` varchar (5) NOT NULL DEFAULT "pt-br",
  `currency_code` varchar (3) NOT NULL DEFAULT "BRL",
  `timezone` varchar (6) NOT NULL DEFAULT "-03:00",
  `creation_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;