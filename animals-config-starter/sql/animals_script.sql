ALTER TABLE animals.creature
ADD CONSTRAINT type_id_fk FOREIGN KEY (type_id) REFERENCES animals.animal_type (id_type);

CREATE TABLE animals.habitat (
	id_area INT PRIMARY KEY,
	area TEXT UNIQUE NOT NULL
);

CREATE TABLE animals.animals_habitats (
	id_animal_type INT,
	id_area INT,
	PRIMARY KEY (id_animal_type, id_area)
);

ALTER TABLE animals.animals_habitats
ADD CONSTRAINT id_animal_type_fk FOREIGN KEY (id_animal_type) REFERENCES animals.animal_type (id_type),
ADD CONSTRAINT id_area_fk FOREIGN KEY (id_area) REFERENCES animals.habitat (id_area);

CREATE TABLE animals.provider (
	id_provider INT PRIMARY KEY,
	name TEXT UNIQUE NOT NULL,
	phone NCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE animals.animals_providers (
	id_animal_type INT,
	id_provider INT,
	PRIMARY KEY (id_animal_type, id_provider)
);

ALTER TABLE animals.animals_providers
ADD CONSTRAINT id_animal_type_fk FOREIGN KEY (id_animal_type) REFERENCES animals.animal_type (id_type),
ADD CONSTRAINT id_provider_fk FOREIGN KEY (id_provider) REFERENCES animals.provider (id_provider);