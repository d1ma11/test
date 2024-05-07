CREATE SCHEMA IF NOT EXISTS animals;

CREATE TABLE IF NOT EXISTS animals.animal_type
(
    id_type   INT PRIMARY KEY,
    type_name VARCHAR(50) UNIQUE NOT NULL,
    is_wild   BOOLEAN            NOT NULL
);

CREATE TABLE IF NOT EXISTS animals.habitat
(
    id_area INT PRIMARY KEY,
    area    TEXT UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS animals.provider
(
    id_provider INT PRIMARY KEY,
    name        TEXT UNIQUE        NOT NULL,
    phone       VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS animals.breed
(
    id_breed   INT PRIMARY KEY,
    breed_name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS animals.animals_habitats
(
    id_animal_type INT,
    id_area        INT,
    PRIMARY KEY (id_animal_type, id_area),
    CONSTRAINT id_animal_type_fk FOREIGN KEY (id_animal_type) REFERENCES animals.animal_type (id_type),
    CONSTRAINT id_area_fk FOREIGN KEY (id_area) REFERENCES animals.habitat (id_area)
);

CREATE TABLE IF NOT EXISTS animals.animals_provider
(
    id_animal_type INT,
    id_provider    INT,
    PRIMARY KEY (id_animal_type, id_provider),
    CONSTRAINT id_animal_type_fk FOREIGN KEY (id_animal_type) REFERENCES animals.animal_type (id_type),
    CONSTRAINT id_provider_fk FOREIGN KEY (id_provider) REFERENCES animals.provider (id_provider)
);

CREATE TABLE IF NOT EXISTS animals.animal
(
    id_animal          INT PRIMARY KEY,
    id_breed           INT            NOT NULL,
    id_type            INT            NOT NULL,
    name               VARCHAR(255)   NOT NULL,
    cost               DECIMAL(10, 2) NOT NULL,
    character          VARCHAR(255),
    birth_date         DATE           NOT NULL,
    secret_information TEXT,
    CONSTRAINT fk_breed FOREIGN KEY (id_breed) REFERENCES animals.breed (id_breed),
    CONSTRAINT fk_animal_type FOREIGN KEY (id_type) REFERENCES animals.animal_type (id_type)
);
