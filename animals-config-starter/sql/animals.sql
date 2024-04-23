CREATE SCHEMA animals;

CREATE TABLE animals.creature (
	id_creature BIGINT PRIMARY KEY,
	name TEXT NOT NULL,
	type_id INT NOT NULL,
	age SMALLINT NOT NULL,
	CONSTRAINT type_id_fk FOREIGN KEY (type_id) REFERENCES animals.animal_type (id_type)
);

CREATE TABLE animals.animal_type (
	id_type INT PRIMARY KEY,
	type_name NCHAR(50) UNIQUE NOT NULL,
	is_wild BOOLEAN NOT NULL
);

CREATE TABLE animals.habitat (
	id_area INT PRIMARY KEY,
	area TEXT UNIQUE NOT NULL
);

CREATE TABLE animals.animals_habitats (
	id_animal_type INT,
	id_area INT,
	PRIMARY KEY (id_animal_type, id_area),
	CONSTRAINT id_animal_type_fk FOREIGN KEY (id_animal_type) REFERENCES animals.animal_type (id_type),
    CONSTRAINT id_area_fk FOREIGN KEY (id_area) REFERENCES animals.habitat (id_area)
);

CREATE TABLE animals.provider (
	id_provider INT PRIMARY KEY,
	name TEXT UNIQUE NOT NULL,
	phone NCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE animals.animals_providers (
	id_animal_type INT,
	id_provider INT,
	PRIMARY KEY (id_animal_type, id_provider),
	CONSTRAINT id_animal_type_fk FOREIGN KEY (id_animal_type) REFERENCES animals.animal_type (id_type),
    CONSTRAINT id_provider_fk FOREIGN KEY (id_provider) REFERENCES animals.provider (id_provider)
);

INSERT INTO animals.animal_type (id_type, type_name, is_wild) VALUES
(1, 'Lion', true),
(2, 'Tiger', true),
(3, 'Elephant', true),
(4, 'Dog', false);

INSERT INTO animals.habitat (id_area, area) VALUES
(1, 'Savannah'),
(2, 'Jungle'),
(3, 'Desert'),
(4, 'Forest');

INSERT INTO animals.provider (id_provider, name, phone) VALUES
(1, 'Wildlife Rescue', '+7-917-458-56-78'),
(2, 'Animal Shelter', '+7-951-216-19-57'),
(3, 'Zoo', '+7-982-659-26-72'),
(4, 'Wildlife Conservation', '+7-901-556-21-05');

INSERT INTO animals.animals_habitats (id_animal_type, id_area) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4);

INSERT INTO animals.animals_providers (id_animal_type, id_provider) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4);

INSERT INTO animals.creature (id_creature, name, type_id, age) VALUES
(1, 'Simba', 1, 5),
(2, 'Tiger', 2, 3),
(3, 'Dumbo', 3, 10),
(4, 'Rex', 4, 2);