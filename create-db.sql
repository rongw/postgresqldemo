-- Role: keeper

DROP ROLE keeper;
CREATE ROLE keeper LOGIN
  ENCRYPTED PASSWORD 'md5b3b89d7c163914d6c65f5b99642e3b3c'
  SUPERUSER INHERIT CREATEDB CREATEROLE REPLICATION;

-- Database: application

--DROP DATABASE application;
CREATE DATABASE application
  WITH OWNER = keeper
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'C'
       LC_CTYPE = 'C'
       CONNECTION LIMIT = -1;

CREATE SCHEMA app_main
  AUTHORIZATION keeper;

CREATE TABLE app_main.candidate
(
  first_name text,
  id serial NOT NULL,
  last_name text,
  profile json,
  CONSTRAINT candidate_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE app_main.candidate
  OWNER TO exchange;

INSERT INTO app_main.candidate( first_name, last_name, profile) VALUES 
('John', 'Smith', '{
  "height": 120,
  "weight": 100,
  "eyeColor": "Yellow",
  "noseShape": "Square"
}');

INSERT INTO app_main.candidate( first_name, last_name, profile) VALUES 
('Panda', 'Smith', '{
  "height": 100,
  "weight": 120,
  "eyeColor": "Red",
  "noseShape": "Square"
}');

INSERT INTO app_main.candidate( first_name, last_name, profile) VALUES 
('Panda', 'Angry', '{
  "height": 120,
  "weight": 100,
  "eyeColor": "Blue",
  "noseShape": "Square"
}');

INSERT INTO app_main.candidate( first_name, last_name, profile) VALUES 
('Flying', 'Panda', '{
  "height": 120,
  "weight": 100,
  "eyeColor": "Red",
  "noseShape": "Square"
}');





