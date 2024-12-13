--INSERÇÃO DE DADOS APENAS VIA MIGRATION OU VIA ENDPOINTS

INSERT INTO restaurant(name, ship_rate, active, open, creation_date, update_date, kitchen_id) VALUES ('Thai Delivery', 2.9, true, true, now(), now(), 1);
INSERT INTO restaurant(name, ship_rate, active, open, creation_date, update_date, kitchen_id) VALUES ('Fast Indian', 1.0, true, true, now(), now(), 2);
INSERT INTO restaurant(name, ship_rate, active, open, creation_date, update_date, kitchen_id) VALUES ('BrazFood', 1.9, true, true, now(), now(), 3);
INSERT INTO restaurant(name, ship_rate, active, open, creation_date, update_date, kitchen_id) VALUES ('Sushi Moto', 3.9, true, true, now(), now(), 4);
INSERT INTO restaurant(name, ship_rate, active, open, creation_date, update_date, kitchen_id) VALUES ('Italin House', 2.0, true, true, now(), now(), 5);

INSERT INTO restaurant_payment_method (restaurant_id, payment_method_id) VALUES (1,1), (1,2), (1,3), (1,4), (1,5), (2,1), (2,2), (2,3), (2,4), (2,5), (3,1), (3,2), (3,3), (3,4), (3,5), (4,1), (4,2), (4,3), (4,4), (4,5), (5,1), (5,2), (5,3), (5,4), (5,5);