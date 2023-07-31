CREATE SCHEMA IF NOT EXISTS `cinema_db_hw4` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;

DROP TABLE IF EXISTS `actor`, `film`, `film_actor`, `cinema` CASCADE;

#CREATE INDEX time_index ON film();

CREATE TABLE IF NOT EXISTS `cinema`(
                                                dtype VARCHAR(31) NOT NULL ,
                                                id INTEGER PRIMARY KEY AUTO_INCREMENT,
                                                name VARCHAR(255),
                                                number_of_halls INTEGER ,
                                                capacity_of_cars INTEGER

);


CREATE TABLE IF NOT EXISTS `film`(
                     id INTEGER PRIMARY KEY AUTO_INCREMENT,
                     title VARCHAR(50) NOT NULL,
                     cinema_id INTEGER NOT NULL,
                     CONSTRAINT FOREIGN KEY (cinema_id) REFERENCES cinema(id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS `actor`(
                      id INTEGER PRIMARY KEY AUTO_INCREMENT,
                      name VARCHAR(60) NOT NULL
);


CREATE TABLE IF NOT EXISTS `film_actor`(
                           film_id INTEGER,
                           actor_id INTEGER,
                           CONSTRAINT PRIMARY KEY (film_id, actor_id),
                           CONSTRAINT FOREIGN KEY (film_id) REFERENCES film(id) ON DELETE CASCADE,
                           CONSTRAINT FOREIGN KEY (actor_id) REFERENCES actor(id) ON DELETE CASCADE
);
