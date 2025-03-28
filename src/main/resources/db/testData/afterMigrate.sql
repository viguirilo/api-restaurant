SET foreign_key_checks = 0;

LOCK TABLES `city` WRITE, `group` WRITE, `group_permission` WRITE, `ordered_item` WRITE, `kitchen` WRITE,
    `oauth2_authorization` WRITE, `oauth2_registered_client` WRITE, `oauth2_authorization_consent` WRITE,
    `order` WRITE, `payment_method` WRITE, `permission` WRITE, `product` WRITE, `restaurant` WRITE,
    `restaurant_payment_method` WRITE, `state` WRITE, `user` WRITE, `user_group` WRITE;

DELETE FROM `city`;
DELETE FROM `group`;
DELETE FROM `group_permission`;
DELETE FROM `ordered_item`;
DELETE FROM `kitchen`;
DELETE FROM `oauth2_authorization`;
DELETE FROM `oauth2_registered_client`;
DELETE FROM `oauth2_authorization_consent`;
DELETE FROM `order`;
DELETE FROM `payment_method`;
DELETE FROM `permission`;
DELETE FROM `product`;
DELETE FROM `restaurant`;
DELETE FROM `restaurant_payment_method`;
DELETE FROM `state`;
DELETE FROM `user`;
DELETE FROM `user_group`;

SET foreign_key_checks = 1;

ALTER TABLE `city` AUTO_INCREMENT = 1;
ALTER TABLE `group` AUTO_INCREMENT = 1;
ALTER TABLE `group_permission` AUTO_INCREMENT = 1;
ALTER TABLE `ordered_item` AUTO_INCREMENT = 1;
ALTER TABLE `kitchen` AUTO_INCREMENT = 1;
ALTER TABLE `order` AUTO_INCREMENT = 1;
ALTER TABLE `payment_method` AUTO_INCREMENT = 1;
ALTER TABLE `permission` AUTO_INCREMENT = 1;
ALTER TABLE `product` AUTO_INCREMENT = 1;
ALTER TABLE `restaurant` AUTO_INCREMENT = 1;
ALTER TABLE `restaurant_payment_method` AUTO_INCREMENT = 1;
ALTER TABLE `state` AUTO_INCREMENT = 1;
ALTER TABLE `user` AUTO_INCREMENT = 1;
ALTER TABLE `user_group` AUTO_INCREMENT = 1;

INSERT INTO kitchen (name) VALUES
('Thai'),
('Indian'),
('Brazilian'),
('Japanese'),
('Italian'),
('Australian'),
('Mexican'),
('Chilean');

INSERT INTO payment_method (description) VALUES
('CASH'),
('DEBIT_CARD'),
('CREDIT_CARD'),
('PIX'),
('ALELO'),
('VALE_REFEIÇÃO'),
('VALE_ALIMENTAÇÃO'),
('FLASH'),
('CHECK');

INSERT INTO permission (name, description) VALUES
('CREATE_CITY', 'Allow an user to create a city'),
('READ_CITY', 'Allow an user to read a city'),
('UPDATE_CITY', 'Allow an user to update a city'),
('DELETE_CITY', 'Allow an user to delete a city'),
('CREATE_ORDER', 'Allow an user to create an order'),
('READ_ORDER', 'Allow an user to read an order'),
('UPDATE_ORDER', 'Allow an user to update an order'),
('DELETE_ORDER', 'Allow an user to delete an order');

INSERT INTO state (name, abbreviation, country) VALUES ('Acre', 'AC', 'Brasil');
INSERT INTO city (name, state_id) VALUES ('Rio Branco', 1);
INSERT INTO state (name, abbreviation, country) VALUES ('Alagoas', 'AL', 'Brasil');
INSERT INTO city (name, state_id) VALUES ('Maceió', 2);
INSERT INTO state (name, abbreviation, country) VALUES ('Amapá', 'AP', 'Brasil');
INSERT INTO city (name, state_id) VALUES ('Macapá', 3);
INSERT INTO state (name, abbreviation, country) VALUES ('Amazonas', 'AM', 'Brasil');
INSERT INTO city (name, state_id) VALUES ('Manaus', 4);
INSERT INTO state (name, abbreviation, country) VALUES ('Bahia', 'BA', 'Brasil');
INSERT INTO city (name, state_id) VALUES ('Salvador', 5);
INSERT INTO state (name, abbreviation, country) VALUES ('Ceará', 'CE', 'Brasil');
INSERT INTO city (name, state_id) VALUES ('Fortaleza', 6);
INSERT INTO state (name, abbreviation, country) VALUES ('Distrito Federal', 'DF', 'Brasil');
INSERT INTO city (name, state_id) VALUES ('Brasília', 7);
INSERT INTO state (name, abbreviation, country) VALUES ('Espírito Santo', 'ES', 'Brasil');
INSERT INTO city (name, state_id) VALUES ('Vitória', 8);
INSERT INTO state (name, abbreviation, country) VALUES ('Goiás', 'GO', 'Brasil');
INSERT INTO city (name, state_id) VALUES ('Goiânia', 9);
INSERT INTO state (name, abbreviation, country) VALUES ('Maranhão', 'MA', 'Brasil');
INSERT INTO city (name, state_id) VALUES ('São Luís', 10);
INSERT INTO state (name, abbreviation, country) VALUES ('Mato Grosso', 'MT', 'Brasil');
INSERT INTO city (name, state_id) VALUES ('Cuiabá', 11);
INSERT INTO state (name, abbreviation, country) VALUES ('Mato Grosso do Sul', 'MS', 'Brasil');
INSERT INTO city (name, state_id) VALUES ('Campo Grande', 12);
INSERT INTO state (name, abbreviation, country) VALUES ('Minas Gerais', 'MG', 'Brasil');
INSERT INTO city (name, state_id) VALUES ('Belo Horizonte', 13);
INSERT INTO state (name, abbreviation, country) VALUES ('Pará', 'PA', 'Brasil');
INSERT INTO city (name, state_id) VALUES ('Belém', 14);
INSERT INTO state (name, abbreviation, country) VALUES ('Paraíba', 'PB', 'Brasil');
INSERT INTO city (name, state_id) VALUES ('João Pessoa', 15);
INSERT INTO state (name, abbreviation, country) VALUES ('Paraná', 'PR', 'Brasil');
INSERT INTO city (name, state_id) VALUES ('Curitiba', 16);
INSERT INTO state (name, abbreviation, country) VALUES ('Pernambuco', 'PE', 'Brasil');
INSERT INTO city (name, state_id) VALUES ('Recife', 17);
INSERT INTO state (name, abbreviation, country) VALUES ('Piauí', 'PI', 'Brasil');
INSERT INTO city (name, state_id) VALUES ('Recife', 18);
INSERT INTO state (name, abbreviation, country) VALUES ('Rio de Janeiro', 'RJ', 'Brasil');
INSERT INTO city (name, state_id) VALUES ('Rio de Janeiro', 19);
INSERT INTO state (name, abbreviation, country) VALUES ('Rio Grande do Norte', 'RN', 'Brasil');
INSERT INTO city (name, state_id) VALUES ('Natal', 20);
INSERT INTO state (name, abbreviation, country) VALUES ('Rio Grande do Sul', 'RS', 'Brasil');
INSERT INTO city (name, state_id) VALUES ('Porto Alegre', 21);
INSERT INTO state (name, abbreviation, country) VALUES ('Rondônia', 'RO', 'Brasil');
INSERT INTO city (name, state_id) VALUES ('Porto Velho', 22);
INSERT INTO state (name, abbreviation, country) VALUES ('Roraima', 'RR', 'Brasil');
INSERT INTO city (name, state_id) VALUES ('Boa Vista', 23);
INSERT INTO state (name, abbreviation, country) VALUES ('Santa Catarina', 'SC', 'Brasil');
INSERT INTO city (name, state_id) VALUES ('Florianópolis', 24);
INSERT INTO state (name, abbreviation, country) VALUES ('São Paulo', 'SP', 'Brasil');
INSERT INTO city (name, state_id) VALUES ('São Paulo', 25);
INSERT INTO state (name, abbreviation, country) VALUES ('Sergipe', 'SE', 'Brasil');
INSERT INTO city (name, state_id) VALUES ('Aracaju', 26);
INSERT INTO state (name, abbreviation, country) VALUES ('Tocantins', 'TO', 'Brasil');
INSERT INTO city (name, state_id) VALUES ('Palmas', 27);

INSERT INTO restaurant (name, ship_rate, active, open, creation_date, update_date, kitchen_id, address_street_or_avenue, address_number, address_complement, address_neighborhood, address_zip_code, address_city_id) VALUES
('Thai Delivery', 2.9, true, true, utc_timestamp, utc_timestamp, 1, "Rua 1", "1", "complemento", "Bairro 1", "30.000-000", 1),
('Fast Indian', 1.0, true, true, utc_timestamp, utc_timestamp, 2, "Rua 2", "2", "complemento", "Bairro 2", "30.000-000", 2),
('BrazFood', 1.9, true, true, utc_timestamp, utc_timestamp, 3, "Rua 3", "3", "complemento", "Bairro 3", "30.000-000", 3),
('Sushi Moto', 3.9, true, true, utc_timestamp, utc_timestamp, 4, "Rua 4", "4", "complemento", "Bairro 4", "30.000-000", 4),
('Italin House', 2.0, true, true, utc_timestamp, utc_timestamp, 5, "Rua 5", "5", "complemento", "Bairro 5", "30.000-000", 5);

INSERT INTO product (name, description, price, active, restaurant_id) VALUES
("Product 1", "Description 1", 1.0, true, 1),
("Product 2", "Description 2", 1.0, true, 2),
("Product 3", "Description 3", 1.0, true, 3),
("Product 4", "Description 4", 1.0, true, 4),
("Product 5", "Description 5", 1.0, true, 5);

INSERT INTO restaurant_payment_method (restaurant_id, payment_method_id) VALUES
(1,1), (1,2), (1,3), (1,4), (1,5), (1,6), (1,7), (1,8), (1,9),
(2,1), (2,2), (2,3), (2,4), (2,5), (2,6), (2,7), (2,8), (2,9),
(3,1), (3,2), (3,3), (3,4), (3,5), (3,6), (3,7), (3,8), (3,9),
(4,1), (4,2), (4,3), (4,4), (4,5), (4,6), (4,7), (4,8), (4,9),
(5,1), (5,2), (5,3), (5,4), (5,5), (5,6), (5,7), (5,8), (5,9);

INSERT INTO `user` (fullname, username, email, password, language_code, currency_code, timezone, creation_date) VALUES
("User One", "user_one", "user.one@provider.com", "$2a$12$/klLSaM6Xopqj.9yDrj.rOg6smSmx7Z1eyaJ599p7ykzDXKqdnF0S", "pt-br", "BRL", "-03:00", utc_timestamp),
("User Two", "user_two", "user.two@provider.com", "$2a$12$/klLSaM6Xopqj.9yDrj.rOg6smSmx7Z1eyaJ599p7ykzDXKqdnF0S", "pt-br", "BRL", "-03:00", utc_timestamp),
("User Three", "user_three", "user.three@provider.com", "$2a$12$/klLSaM6Xopqj.9yDrj.rOg6smSmx7Z1eyaJ599p7ykzDXKqdnF0S", "pt-br", "BRL", "-03:00", utc_timestamp),
("User Four", "user_four", "user.four@provider.com", "$2a$12$/klLSaM6Xopqj.9yDrj.rOg6smSmx7Z1eyaJ599p7ykzDXKqdnF0S", "pt-br", "BRL", "-03:00", utc_timestamp),
("User Five", "user_five", "user.five@provider.com", "$2a$12$/klLSaM6Xopqj.9yDrj.rOg6smSmx7Z1eyaJ599p7ykzDXKqdnF0S", "pt-br", "BRL", "-03:00", utc_timestamp);

INSERT INTO `group` (name) VALUES
("Group 1"),
("Group 2"),
("Group 3"),
("Group 4"),
("Group 5");

INSERT INTO `order` (sub_total, ship_rate, total_value, creation_date, restaurant_id, customer_id, payment_method_id, address_street_or_avenue, address_number, address_complement, address_neighborhood, address_zip_code, address_city_id, status) VALUES
(5.0, 2.5, 7.5, utc_timestamp, 1, 1, 1, "Rua 1", "1", "", "Neighborhood", "30.000-000", 1, "CREATED"),
(5.0, 2.5, 7.5, utc_timestamp, 2, 2, 2, "Rua 2", "2", "", "Neighborhood", "30.000-000", 2, "CREATED"),
(5.0, 2.5, 7.5, utc_timestamp, 3, 3, 3, "Rua 3", "3", "", "Neighborhood", "30.000-000", 3, "CREATED"),
(5.0, 2.5, 7.5, utc_timestamp, 4, 4, 4, "Rua 4", "4", "", "Neighborhood", "30.000-000", 4, "CREATED"),
(5.0, 2.5, 7.5, utc_timestamp, 5, 5, 5, "Rua 5", "5", "", "Neighborhood", "30.000-000", 5, "CREATED");

INSERT INTO ordered_item (order_id, product_id, observation, quantity, unit_price, total_price) VALUES
(1, 1, "Observation 1", 1, 1.0, 1.0),
(2, 2, "Observation 1", 1, 1.0, 1.0),
(3, 3, "Observation 1", 1, 1.0, 1.0),
(4, 4, "Observation 1", 1, 1.0, 1.0),
(5, 5, "Observation 1", 1, 1.0, 1.0);

INSERT INTO group_permission(group_id, permission_id) VALUES
(1, 1), (1, 5),
(2, 2), (2, 6),
(3, 3), (3, 7),
(4, 4), (4, 8);

INSERT INTO user_group (user_id, group_id) VALUES
(1, 1), (1, 2), (1, 3),
(2, 2),
(3, 3),
(4, 4),
(5, 5);

INSERT INTO oauth2_registered_client (id, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_name, client_authentication_methods, authorization_grant_types, redirect_uris, post_logout_redirect_uris, scopes, client_settings, token_settings) VALUES
('1', 'app-restaurant', '2025-02-28 18:28:31', '$2a$10$XZFN0tn4p5ejyO.mCIgtGu/O0fqHSz8VZX2TZUC3f9Hb57ykqyDAG', NULL, '1', 'client_secret_basic', 'client_credentials','', '', 'READ', '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}', '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.x509-certificate-bound-access-tokens":false,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",1800.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",3600.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000],"settings.token.device-code-time-to-live":["java.time.Duration",300.000000000]}'),
('2', 'app-restaurant-mobile', '2025-02-28 18:28:31', '$2a$10$EtQhSw1NSosY.E5bxQFrEus9rWdrLdGHb5l7e2uJ0y1MqbH0F6OO2', NULL, '2', 'client_secret_basic', 'refresh_token,authorization_code', 'http://127.0.0.1:8000/authorized', 'http://127.0.0.1:8000/', 'READ,WRITE', '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":true}', '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":false,"settings.token.x509-certificate-bound-access-tokens":false,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",900.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",86400.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000],"settings.token.device-code-time-to-live":["java.time.Duration",300.000000000]}');

UNLOCK TABLES;