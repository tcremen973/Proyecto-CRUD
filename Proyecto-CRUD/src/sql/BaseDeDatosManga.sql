DROP DATABASE IF EXISTS manga;
CREATE DATABASE manga;
USE manga;

-- Tabla autor
CREATE TABLE autor (
  id INT PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(50),
  pais VARCHAR(50),
  fecha_nacimiento DATE,
  fecha_defuncion DATE
);

-- Tabla editorial
CREATE TABLE editorial (
  id INT PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(50),
  pais VARCHAR(50),
  fecha_fundacion DATE,
  direccion VARCHAR(100)
);

-- Tabla manga
CREATE TABLE manga (
  id INT PRIMARY KEY AUTO_INCREMENT,
  titulo VARCHAR(100),
  genero VARCHAR(50),
  sinopsis VARCHAR(500),
  fecha_publicacion DATE,
  id_autor INT,
  id_editorial INT,
  FOREIGN KEY (id_autor) REFERENCES autor(id) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (id_editorial) REFERENCES editorial(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Insertar datos en las tablas
INSERT INTO autor (id, nombre, pais, fecha_nacimiento, fecha_defuncion) VALUES
  (1, 'Akira Toriyama', 'Japón', '1955-04-05', NULL),
  (2, 'Eiichiro Oda', 'Japón', '1975-01-01', NULL),
  (3, 'Koyoharu Gotouge', 'Japón', '1989-05-05', NULL);

INSERT INTO editorial (id, nombre, pais, fecha_fundacion, direccion) VALUES
  (1, 'Shueisha', 'Japón', '1925-01-01', 'Tokio, Japón'),
  (2, 'Shonen Jump', 'Japón', '1968-07-02', 'Tokio, Japón');

INSERT INTO manga (id, titulo, genero, sinopsis, fecha_publicacion, id_autor, id_editorial) VALUES
  (1, 'Dragon Ball', 'Acción, Aventura, Comedia', 'Un joven aventurero llamado Goku busca las siete esferas del dragón para poder pedir un deseo.', '1984-11-20', 1, 1),
  (2, 'One Piece', 'Acción, Aventura, Comedia', 'Un joven pirata llamado Monkey D. Luffy busca el tesoro más grande del mundo, conocido como "One Piece".', '1997-07-22', 2, 2),
  (3, 'Kimetsu no Yaiba', 'Acción, Aventura, Fantasía', 'Un joven llamado Tanjiro se convierte en un cazador de demonios para vengar a su familia y encontrar una cura para su hermana.', '2016-02-15', 3, 1),
  (4, 'Naruto', 'Acción, Aventura, Comedia', 'Un joven ninja llamado Naruto busca convertirse en el Hokage de su aldea y ser reconocido como un héroe.', '1999-09-21', NULL, 2),
  (5, 'Death Note', 'Misterio, Sobrenatural, Thriller psicológico', 'Un joven estudiante llamado Light Yagami encuentra un cuaderno mágico que le permite matar a cualquier persona cuyo nombre escriba en él.', '2003-12-01', NULL, 1),
  (6, 'Attack on Titan', 'Acción, Drama, Horror', 'La humanidad vive dentro de tres muros gigantes para protegerse de los titanes, gigantes humanoides que devoran a los humanos.', '2009-09-09', NULL, 2),
  (7, 'Jujutsu Kaisen', 'Acción, Aventura, Fantasía', 'Un joven llamado Yuji Itadori se convierte en un cazador de demonios para proteger a la humanidad de las criaturas malditas.', '2018-03-05', NULL, 1);
