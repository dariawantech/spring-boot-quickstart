-- drop sequence d_user_id_seq;
-- drop table d_user

CREATE SEQUENCE d_user_id_seq;

CREATE TABLE d_user (
   id integer DEFAULT nextval('d_user_id_seq') PRIMARY KEY,
   username varchar(255) UNIQUE NOT NULL,
   password varchar(255) NOT NULL,
   firstname varchar(50) DEFAULT NULL,
   lastname varchar(50) DEFAULT NULL,
   enabled boolean DEFAULT NULL,
   email varchar(255) DEFAULT NULL
)

ALTER SEQUENCE d_user_id_seq
OWNED BY d_user.id;

insert into d_user(username, password, firstname, lastname, enabled) 
values 
('saitama', '123', 'Saitama', null, true),
('genos', '123', 'Genos', null, true); 

GRANT ALL ON table d_user TO barista;
GRANT ALL ON sequence d_user_id_seq TO barista;
-- GRANT ALL ON schema public TO barista;