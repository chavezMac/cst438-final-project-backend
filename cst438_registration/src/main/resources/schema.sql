create Table User (
	user_id int NOT NULL AUTO_INCREMENT,
	alias varchar(50),
	password varchar(50),
	role varchar(10),
	PRIMARY KEY (user_id)
);

create Table City (
	city_id int NOT NULL AUTO_INCREMENT,
	name varchar(50),
	temperature int,
	max int,
	min int,
	icon varchar(10),
	PRIMARY KEY (city_id)
);

create Table Added (
	user_id int NOT NULL AUTO_INCREMENT,
	city_id int NOT NULL AUTO_INCREMENT,
	name varchar(50),
	temperature int,
	max int,
	min int,
	icon varchar(10),
	PRIMARY KEY (user_id, city_id),
	FOREIGN KEY (user_id) REFERENCES User(user_id),
	FOREIGN KEY (city_id) REFERENCES City(city_id)
);