CREATE TABLE characters (
                       id serial NOT NULL primary key,
                       name varchar(15),
                       description varchar(250),
                       image text

);

CREATE TABLE comics (
                      id serial NOT NULL primary key ,
                      title varchar(50),
                      description varchar (250)
);

CREATE TABLE characters_comics (
                           character_id int NOT NULL,
                           comic_id int NOT NULL,
                           primary key (character_id, comic_id),
                           foreign key (character_id) references characters (id),
                           foreign key (comic_id) references comics (id));

/*INSERT INTO characters (name,description)
values ('Spider_Man', 'Hero from one movie'),
('Halk','Hero from another movie');*/

INSERT INTO comics (title, description)
VALUES
('Batman comes back','after long absence hero coming back'),
       ('Evil among as', 'this comics is in unlimited version');