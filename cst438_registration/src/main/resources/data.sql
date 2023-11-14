INSERT INTO User (user_id, alias, password, role)
VALUES
(1, 'ryan', 'ryan', 'user'),
(2, 'michael', 'michael', 'user'),
(3, 'admin', 'admin', 'admin');

INSERT INTO City (city_id, name, temperature, max, min, icon)
VALUES
(1, 'Salinas', 300, 270, 320, '10d'),
(2, 'Monterey',270, 275, 250, '10d'),
(3, 'Marina', 270, 270, 245, '10d');;

INSERT INTO Added (user_id, city_id, name, temperature, max, min, icon)
VALUES
(1, 1, 'Salinas', 300, 270, 320, '10d'),
(1, 3, 'Marina', 270, 270, 245, '10d'),
(2, 1, 'Salinas', 300, 270, 320, '10d'),
(2, 2, 'Monterey', 270, 275, 250, '10d');