INSERT INTO User (user_id, alias, password, role)
VALUES
(1, 'ryan', '$2a$10$7laNsTqgnRc8mBUJ3u7rfeLDygfhTvfYdXxruEnxNH59NSjfVkaVC', 'user'),
(2, 'michael', '$2a$10$5S0H3G9tKHkmApatums/7.g8mgTZ0aN2SZCqxcfW.zm74Agk/UhRy', 'user'),
(3, 'admin', '$2a$10$EneZBkJP0cYYZcANrBq/KOONs3LZSwEyG2uu30hVTRfHgIZCTpl/W', 'admin');

INSERT INTO City (city_id, name, temperature, max, min, icon)
VALUES
(1, 'Marina', 65, 70, 60, '10d'),
(2, 'Aptos', 60, 65, 50, '05d');