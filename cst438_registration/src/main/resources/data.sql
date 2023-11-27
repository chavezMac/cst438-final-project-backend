INSERT INTO User (user_id, alias, password, role)
VALUES
(1, 'ryan', '$2a$10$7laNsTqgnRc8mBUJ3u7rfeLDygfhTvfYdXxruEnxNH59NSjfVkaVC', 'user'),
(2, 'michael', '$2a$10$5S0H3G9tKHkmApatums/7.g8mgTZ0aN2SZCqxcfW.zm74Agk/UhRy', 'user'),
(3, 'admin', '$2a$10$EneZBkJP0cYYZcANrBq/KOONs3LZSwEyG2uu30hVTRfHgIZCTpl/W', 'admin');

INSERT INTO City (city_id, name, temperature, max, min, icon)
VALUES
(1, 'Salinas', 300, 270, 320, '10d'),
(2, 'Monterey',270, 275, 250, '10d'),
(3, 'Marina', 270, 270, 245, '10d'),
(4, 'Watsonville', 300, 270, 320, '10d'),
(5, 'Santa Cruz', 300, 270, 320, '10d'),
(6, 'San Jose', 300, 270, 320, '10d'),
(7, 'Los Angeles', 300, 270, 320, '10d'),
(8, 'New York', 300, 270, 320, '10d'),
(9, 'London', 300, 270, 320, '10d'),
(10, 'Tokyo', 300, 270, 320, '10d');

INSERT INTO Added (user_id, city_id, name, temperature, max, min, icon)
VALUES
(1, 1, 'Salinas', 300, 270, 320, '10d'),
(1, 3, 'Marina', 270, 270, 245, '10d'),
(2, 1, 'Salinas', 300, 270, 320, '10d'),
(2, 2, 'Monterey', 270, 275, 250, '10d');