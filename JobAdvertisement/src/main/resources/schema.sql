DROP TABLE positions IF EXISTS;

DROP TABLE clients IF EXISTS;

CREATE TABLE clients (
  id INT GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1) NOT NULL,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(200) NOT NULL,
  PRIMARY KEY (id)
);

CREATE UNIQUE INDEX index_email ON clients (email);

CREATE TABLE positions (
  id INT GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1) NOT NULL,
  title VARCHAR(50) NOT NULL,
  location VARCHAR(50) NOT NULL,
  client_id INT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_position_client FOREIGN KEY (client_id) REFERENCES clients(id)
);
