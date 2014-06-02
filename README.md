OdroidX2-unix-project
=====================
How to create mysql DB

Create database Odroid;

CREATE USER 'Odroid'@'localhost' IDENTIFIED BY 'Odroid123';

GRANT ALL PRIVILEGES ON *.* TO 'Odroid'@'localhost' With grant option;
