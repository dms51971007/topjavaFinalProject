DROP TABLE user_roles IF EXISTS;
DROP TABLE meals IF EXISTS;
DROP TABLE menu IF EXISTS ;
DROP TABLE votes IF EXISTS ;
DROP TABLE restaurant IF EXISTS ;
DROP TABLE users IF EXISTS ;

DROP SEQUENCE global_seq IF EXISTS;

CREATE SEQUENCE GLOBAL_SEQ AS INTEGER START WITH 100000;

CREATE TABLE users
(
  id               INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
  name             VARCHAR(255),
  email            VARCHAR(255)         NOT NULL,
  password         VARCHAR(255)         NOT NULL,
  registered       TIMESTAMP DEFAULT now(),
  enabled          BOOLEAN   DEFAULT TRUE,
  calories_per_day INTEGER DEFAULT 2000 NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON USERS (email);

CREATE TABLE user_roles
(
  user_id INTEGER NOT NULL,
  role    VARCHAR(255),
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY ( user_id ) REFERENCES USERS (id) ON DELETE CASCADE
);

CREATE TABLE meals
(
  id          INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
  date_time   TIMESTAMP    NOT NULL,
  description VARCHAR(255) NOT NULL,
  calories    INT          NOT NULL,
  user_id     INTEGER      NOT NULL,
  FOREIGN KEY ( user_id ) REFERENCES USERS (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX meals_unique_user_datetime_idx ON meals (user_id, date_time);

CREATE TABLE restaurant
(
  id          INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
  name        VARCHAR(100) NOT NULL
);

CREATE TABLE menu
(
  id            INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
  name          VARCHAR(100) NOT NULL,
  price         DOUBLE PRECISION,
  data          DATE,
  restaurant_id INTEGER NOT NULL,
  FOREIGN KEY ( restaurant_id ) REFERENCES restaurant (id) ON DELETE CASCADE
);


CREATE TABLE votes
(
  id            INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
  data          DATE,
  user_id INTEGER NOT NULL,
  CONSTRAINT user_data_idx UNIQUE ( data, user_id),
  restaurant_id INTEGER NOT NULL,
  FOREIGN KEY ( restaurant_id ) REFERENCES restaurant (id) ON DELETE CASCADE,
  FOREIGN KEY ( user_id ) REFERENCES USERS (id) ON DELETE CASCADE

);
