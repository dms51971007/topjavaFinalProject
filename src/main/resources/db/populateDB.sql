DELETE FROM user_roles;
DELETE FROM menu;
DELETE FROM restaurant;
DELETE FROM users;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001),
  ('ROLE_USER', 100001);

INSERT INTO restaurant (id, name) VALUES (300010,'rest1'), (300011,'rest2');

INSERT INTO menu (id, name, price, data, restaurant_id) VALUES
  (200010, 'pizza', 5.11, '2017-03-10', 300010),
  (200011, 'soup', 2.12, '2017-03-10', 300010);

