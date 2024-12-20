SET foreign_key_checks = 0;

DELETE FROM `city`;
DELETE FROM `group`;
DELETE FROM `group_permission`;
DELETE FROM `item_ordered`;
DELETE FROM `kitchen`;
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
ALTER TABLE `item_ordered` AUTO_INCREMENT = 1;
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

INSERT INTO restaurant(name, ship_rate, active, open, creation_date, update_date, kitchen_id, address_street_or_avenue, address_number, address_complement, address_neighborhood, address_zip_code, address_city_id) VALUES
('Thai Delivery', 2.9, true, true, now(), now(), 1, "Rua 1", "1", "complemento", "Bairro 1", "30.000-000", 1),
('Fast Indian', 1.0, true, true, now(), now(), 2, "Rua 2", "2", "complemento", "Bairro 2", "30.000-000", 1),
('BrazFood', 1.9, true, true, now(), now(), 3, "Rua 3", "3", "complemento", "Bairro 3", "30.000-000", 1),
('Sushi Moto', 3.9, true, true, now(), now(), 4, "Rua 4", "4", "complemento", "Bairro 4", "30.000-000", 1),
('Italin House', 2.0, true, true, now(), now(), 5, "Rua 5", "5", "complemento", "Bairro 5", "30.000-000", 1);

INSERT INTO restaurant_payment_method (restaurant_id, payment_method_id) VALUES
(1,1), (1,2), (1,3), (1,4), (1,5), (1,6), (1,7), (1,8), (1,9),
(2,1), (2,2), (2,3), (2,4), (2,5), (2,6), (2,7), (2,8), (2,9),
(3,1), (3,2), (3,3), (3,4), (3,5), (3,6), (3,7), (3,8), (3,9),
(4,1), (4,2), (4,3), (4,4), (4,5), (4,6), (4,7), (4,8), (4,9),
(5,1), (5,2), (5,3), (5,4), (5,5), (5,6), (5,7), (5,8), (5,9);