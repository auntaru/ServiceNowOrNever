CREATE DATABASE IF NOT EXISTS tutorialDb;

USE tutorialDb;

DROP TABLE IF EXISTS user_table;

CREATE TABLE user_table (
  user_id int(20) NOT NULL,
  user_name varchar(255) NULL,
  created_by VARCHAR (255) NOT NULL,
  created_date DATE NOT NULL,
  PRIMARY KEY (user_id)
);