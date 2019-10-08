-- drop table d_user

CREATE TABLE d_user (
   id SERIAL PRIMARY KEY,
   username varchar(255) UNIQUE NOT NULL,
   password varchar(255) NOT NULL,
   firstname varchar(50) DEFAULT NULL,
   lastname varchar(50) DEFAULT NULL,
   enabled boolean DEFAULT NULL,
   email varchar(255) DEFAULT NULL
)

insert into d_user(username, password, firstname, lastname, enabled) 
values 
('saitama', '123', 'Saitama', null, true),
('genos', '123', 'Genos', null, true); 

GRANT ALL ON table d_user TO barista;