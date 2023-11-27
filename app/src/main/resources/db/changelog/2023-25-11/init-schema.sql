-- liquibase formatted sql
-- changeset ifeanyichukwuOtiwa:2023-25-11-init-schema


CREATE TABLE IF NOT EXISTS result_info(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    seed VARCHAR(50) NOT NULL,
    results VARCHAR(50) NOT NULL,
    page VARCHAR(50) NOT NULL,
    version VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS person_name(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    first VARCHAR(255) NOT NULL,
    last VARCHAR(255) NOT NULL
);


CREATE TABLE IF NOT EXISTS date_of_birth(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    date DATETIME NOT NULL,
    age INT NOT NULL
);

CREATE TABLE IF NOT EXISTS person(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    gender VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    cell VARCHAR(255) NOT NULL,
    nat VARCHAR(255) NOT NULL,
    fk_person_name BIGINT NOT NULL,
    fk_date_of_birth BIGINT NOT NULL,
    fk_result_info BIGINT NOT NULL,
    FOREIGN KEY (fk_person_name) REFERENCES person_name(id),
    FOREIGN KEY (fk_date_of_birth) REFERENCES date_of_birth(id),
    FOREIGN KEY (fk_result_info) REFERENCES result_info(id)
);