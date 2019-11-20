CREATE TABLE agent_rt(
   id serial PRIMARY KEY,
   name VARCHAR (50) NOT NULL,
   skill_ids integer[] NOT NULL,
   created_date TIMESTAMP NOT NULL
);

CREATE TABLE skill_rt(
   id serial PRIMARY KEY,
   name VARCHAR (50) UNIQUE NOT NULL
);

CREATE TABLE task_assignments_rt(
   task_id serial PRIMARY KEY,
   agent_id integer REFERENCES agent_rt (id),
   skill_ids integer[] NOT NULL,
   priority VARCHAR (10) NOT NULL,
   task_status VARCHAR (15),
   start_date TIMESTAMP NOT NULL,
   complete_date TIMESTAMP
);

CREATE SEQUENCE task_id_seq INCREMENT 1 START 1;